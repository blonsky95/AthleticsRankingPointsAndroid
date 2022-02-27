package com.example.athleticsrankingpoints.presentation.screens.simulatorscreen

import androidx.compose.runtime.mutableStateOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athleticsrankingpoints.Strings
import com.example.athleticsrankingpoints.data.entities.RankingScorePerformanceData
import com.example.athleticsrankingpoints.domain.interfaces.EventGroupsRepository
import com.example.athleticsrankingpoints.domain.interfaces.RankingScorePerformanceRepository
import com.example.athleticsrankingpoints.domain.models.AthleticsEvent
import com.example.athleticsrankingpoints.domain.models.AthleticsEvent.Companion.getSampleEvent
import com.example.athleticsrankingpoints.domain.models.AthleticsEvent.Companion.getWindPoints
import com.example.athleticsrankingpoints.domain.models.EventGroup
import com.example.athleticsrankingpoints.domain.models.PerformanceUnitsAware
import com.example.athleticsrankingpoints.toIntsArray
import kotlinx.coroutines.launch
import kotlin.math.floor

data class SimulatorDataModel(
  val eventGroupName: String,
  val loadPerformanceName: String
)

data class SnackBarModel(
  var isShowing:Boolean,
  var text:String
)

class EventGroupSimulatorViewModel(
  eventGroupsRepository: EventGroupsRepository, private val simulatorDataModel: SimulatorDataModel,
  private val rankingScorePerformanceRepository: RankingScorePerformanceRepository
):ViewModel() {

  var snackBarModel =  mutableStateOf(SnackBarModel(false,""))
    private set

  private var size:Int = 0

  var loadedRankingScorePerformanceData :RankingScorePerformanceData? = null

  private var selectedEventGroup = MutableLiveData(
    EventGroup.getSampleEventGroup()
  )
  fun getSelectedEventGroup() : LiveData<EventGroup> = selectedEventGroup

  init {
    viewModelScope.launch {
      eventGroupsRepository.getEventGroupByName(simulatorDataModel.eventGroupName)?.let {
        selectedEventGroup.value=it
        listOfSelectedEvents.value = initListOfEvents(it.sMainEvent, it.sMinNumberPerformancesGroup)
        listOfPerformances.value=initListOfPerformances(it.getAllEventsInGroup().first())
      }
      if (simulatorDataModel.loadPerformanceName!=RankingScorePerformanceData.NEW_ENTRY) {
        rankingScorePerformanceRepository.getRankingScorePerformanceByName(simulatorDataModel.loadPerformanceName)?.let {
          loadedRankingScorePerformanceData=it
          loadAllLoadedValues(it)
        }
      }
    }
  }

  //INITIALIZATIONS
  private fun loadAllLoadedValues(performanceData: RankingScorePerformanceData) {
    scoreTitle.postValue(performanceData.name)
    selectedEventGroup.postValue(performanceData.eventGroup)
    listOfPerformances.postValue(performanceData.performances)
    listOfPerformancePoints.postValue(performanceData.performancesPoints)
    listOfWinds.postValue(performanceData.winds)
    listOfWindsPoints.postValue(performanceData.windsPoints)
    listOfPlacementPoints.postValue(performanceData.placementPoints)
    listOfSelectedEvents.postValue(performanceData.selectedEvents)
  }

  private var showDeleteDialog = MutableLiveData(
    false
  )
  fun getShowDeleteDialog() : LiveData<Boolean> = showDeleteDialog

  private var showNameOverwriteDialog = MutableLiveData(
    false
  )
  fun getShowNameOverwriteDialog() : LiveData<Boolean> = showNameOverwriteDialog

  private var showErrorValidatingDialog:MutableLiveData<String?> = MutableLiveData(
    null
  )
  fun getShowErrorValidatingDialog() : LiveData<String?> = showErrorValidatingDialog


  private var scoreTitle = MutableLiveData("")
  fun getTitle(): LiveData<String> = scoreTitle

  private var listOfPerformances = MutableLiveData(
        initListOfPerformances()
  )

  fun getListOfPerformances() : LiveData<List<PerformanceUnitsAware>> = listOfPerformances

  private var listOfPerformancePoints = MutableLiveData(
    initListOfInts())
  fun getListOfPerformancePoints() : LiveData<List<String>> = listOfPerformancePoints

  private var listOfWinds = MutableLiveData(
    initListOfDoubles())
  fun getListOfWinds() : LiveData<List<String>> = listOfWinds

  private var listOfWindsPoints = MutableLiveData(
    initListOfInts())
  fun getListOfWindsPoints() : LiveData<List<String>> = listOfWindsPoints

  private var listOfPlacementPoints = MutableLiveData(
    initListOfInts())
  fun getListOfPlacementPoints() : LiveData<List<String>> = listOfPlacementPoints

  private var listOfSelectedEvents = MutableLiveData(
    initListOfEvents(athleticsEvent = selectedEventGroup.value?.sMainEvent?: getSampleEvent(), size = selectedEventGroup.value?.sMinNumberPerformancesGroup?:1))
  fun getListOfSelectedEvents() : LiveData<List<AthleticsEvent>> = listOfSelectedEvents

  private var rankingScore = MutableLiveData("0")
  fun getRankingScore() : LiveData<String> = rankingScore

  private fun initListOfDoubles(): List<String> {
    size = selectedEventGroup.value!!.sMinNumberPerformancesGroup
    val array1 = arrayListOf<String>()
    for (index in 1..size) {
      array1.add("")
    }
    return array1
  }

  private fun initListOfInts(): List<String> {
    size = selectedEventGroup.value!!.sMinNumberPerformancesGroup
    val array1 = arrayListOf<String>()
    for (index in 1..size) {
      array1.add("")
    }
    return array1
  }

  private fun initListOfEvents(athleticsEvent: AthleticsEvent, size: Int): List<AthleticsEvent> {
    val array1 = arrayListOf<AthleticsEvent>()
    for (index in 1..size) {
      array1.add(athleticsEvent)
    }
    return array1
  }

  private fun initListOfPerformances(event: AthleticsEvent = getSampleEvent()): List<PerformanceUnitsAware> {
    size = selectedEventGroup.value!!.sMinNumberPerformancesGroup
    val array1 = arrayListOf<PerformanceUnitsAware>()
    for (index in 1..size) {
      array1.add(PerformanceUnitsAware.getDefault(event = event))
    }
    return array1
  }

  //SAVING PERFORMANCE

  private fun savePerformance() {
    val rankingScore = collectClassData()
    viewModelScope.launch {
      rankingScorePerformanceRepository.isEntryNameFree(rankingScore.name).apply {
        if (this){
          confirmSavePerformance()
        } else {
          showNameOverwriteDialog.value=true
        }
      }
    }
  }

  private fun confirmSavePerformance() {
    viewModelScope.launch {
      saveNewPerformanceToRepository()
    }
  }

  private suspend fun saveNewPerformanceToRepository() {
    val rankingScore = collectClassData()
    rankingScorePerformanceRepository.saveRankingScorePerformance(rankingScore)
    showSnackBar(Strings.PerformanceSavedSuccesfullyText)
  }

  //UPDATING PERFORMANCE

  fun confirmUpdatePerformance() {
    viewModelScope.launch {
      updatePerformanceToRepository()
    }
  }

  private suspend fun updatePerformanceToRepository() {
    loadedRankingScorePerformanceData?.let {
      rankingScorePerformanceRepository.updateRankingScorePerformance(collectClassData().copy(scoreId = it.scoreId))
    }
    showSnackBar(Strings.PerformanceUpdatedSuccesfullyText)
  }

  //DELETE PERFORMANCE


  fun confirmDeletePerformance() {
    viewModelScope.launch {
      deletePerformanceFromRepo()
    }
  }

  private suspend fun deletePerformanceFromRepo() {
    loadedRankingScorePerformanceData?.let {
      rankingScorePerformanceRepository.deleteRankingScorePerformance(it)
    }
    showSnackBar(Strings.PerformanceDeletedSuccesfullyText)
  }

  //USER ACTIONS

  fun saveButtonPressed() =
    getFieldValidation().takeIf { !it.isNullOrEmpty() }?.let { showDialogWithError(it) } ?: savePerformance()

  fun deleteButtonPressed() {
    showDeleteDialog.value=true
  }

  private fun showSnackBar(text: String) {
    snackBarModel.value = SnackBarModel(true, text)
  }

  fun hideDeleteDialog() {
    showDeleteDialog.value=false
  }

  fun hideNameOverwriteDialog() {
    showNameOverwriteDialog.value=false
  }

  fun hideErrorValidateDialog() {
    showErrorValidatingDialog.value=null
  }

  private fun showDialogWithError(s: String) {
    showErrorValidatingDialog.postValue(s)
  }

  private fun getFieldValidation():String? {
    var errorMessage = ""
    if (scoreTitle.value?.length?:0 <= 2)  {
      errorMessage = "Name of ranking score must be longer than 2 characters"
    }
    if (!isNumberPerformancesOfMainEventValid()) {
      errorMessage = "Not enough main event performances"
    }
    return errorMessage
  }

  private fun isNumberPerformancesOfMainEventValid():Boolean{
    selectedEventGroup.value?.let { eventGroup ->
      var mainEventCounter = 0
      listOfSelectedEvents.value?.let {
        for (event in it) {
          if (event==eventGroup.sMainEvent) {
            mainEventCounter++
          }
        }
      }
      return mainEventCounter>=eventGroup.sMinNumberPerformancesMainEvent
    }
    return false
  }

  //DATA & MODEL HANDLING

  private fun collectClassData(): RankingScorePerformanceData {
    return RankingScorePerformanceData(
      name = scoreTitle.value!!,
      eventGroup = selectedEventGroup.value!!,
      performances = listOfPerformances.value!!,
      performancesPoints = listOfPerformancePoints.value!!,
      winds = listOfWinds.value!!,
      windsPoints = listOfWindsPoints.value!!,
      placementPoints = listOfPlacementPoints.value!!,
      selectedEvents = listOfSelectedEvents.value!!,
      rankingScore = getRankingScore(listOfPerformancePoints.value!!,listOfPlacementPoints.value!!,listOfWindsPoints.value!! )
    )
  }

  fun updateTitle(sTitle: String) {
    scoreTitle.postValue(sTitle)
  }

  fun updatePerformancesList(index: Int, performance: PerformanceUnitsAware) {
    val performancesArrayValue = listOfPerformances.value!!.toMutableList()

    performancesArrayValue[index] = performance
    listOfPerformances.postValue(performancesArrayValue.toList())

    updatePerformancePointsList(index = index, performance = performance.getPerformanceAbsoluteValue())
  }

  fun updateWindList(index: Int, wind: String) {
    val windsValue = listOfWinds.value!!.toMutableList()
    windsValue[index] = wind
    listOfWinds.postValue(windsValue.toList())
    updateWindPointsList(index, getWindPoints(wind).toString())
  }

  fun updateSelectedEvent(index: Int, event: AthleticsEvent) {
    val selectedEventsValue = listOfSelectedEvents.value!!.toMutableList()
    selectedEventsValue[index] = event
    listOfSelectedEvents.postValue(selectedEventsValue.toList())
    updatePerformancesList(index, PerformanceUnitsAware.getDefault(event = event))
    updatePerformancePointsList(index = index, performance = listOfPerformances.value!![index].getPerformanceAbsoluteValue(), event = event)
  }

  private fun updatePerformancePointsList(index: Int, performance: String, event: AthleticsEvent? = null) {
    val pointsPerfArrayValue = listOfPerformancePoints.value!!.toMutableList()
    pointsPerfArrayValue[index] = getPointsForGivenIndex(numberPerformance = index, performance = performance, event = event) //do the actual points
    listOfPerformancePoints.postValue(pointsPerfArrayValue.toList())
  }

  private fun updateWindPointsList(index: Int, points: String) {
    val pointsWindArrayValue = listOfWindsPoints.value!!.toMutableList()
    pointsWindArrayValue[index] = points
    listOfWindsPoints.postValue(pointsWindArrayValue.toList())
  }

  fun updatePlacementPointsList(index: Int, points: String) {
    val placementPointsValue = listOfPlacementPoints.value!!.toMutableList()
    placementPointsValue[index] = points
    listOfPlacementPoints.postValue(placementPointsValue.toList())
  }

  fun getRankingScore(perfPoints:List<String>, placementPoints:List<String>, windPoints:List<String>):String  =
//    getAverage(total = perfPoints.toIntsArray().sum() + placementPoints.toIntsArray().sum() + windPoints.toIntsArray().sum(), size = size)
    getAverage(total = (
        (listOfPerformancePoints.value?.toIntsArray()?.sum()?:0)
            + (listOfPlacementPoints.value?.toIntsArray()?.sum()?:0)
            + (listOfWindsPoints.value?.toIntsArray()?.sum()?:0)
        ),
      size = listOfSelectedEvents.value?.size?:0)


  private fun getAverage(total:Int, size:Int):String {
    return floor(total.toDouble()/size).toInt().toString()
  }

  /**
   * If this is called by event change, event wont be null.
  This function uses the current livedata values so it wouldnt be safe with out doing it in this way.
   */
  private fun getPointsForGivenIndex(numberPerformance: Int, performance: String, event: AthleticsEvent? = null): String {
    return event?.getPointsString(performance = performance) ?: listOfSelectedEvents.value!![numberPerformance].getPointsString(performance = performance)
  }

}