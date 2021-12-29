package com.example.athleticsrankingpoints.presentation.screens.simulatorscreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athleticsrankingpoints.data.entity.RankingScorePerformanceData
import com.example.athleticsrankingpoints.domain.interfaces.EventGroupsRepository
import com.example.athleticsrankingpoints.domain.interfaces.RankingScorePerformanceRepository
import com.example.athleticsrankingpoints.domain.models.AthleticsEvent
import com.example.athleticsrankingpoints.domain.models.AthleticsEvent.Companion.getWindPoints
import com.example.athleticsrankingpoints.domain.models.EventGroup
import com.example.athleticsrankingpoints.toIntsArray
import kotlinx.coroutines.launch
import kotlin.math.floor

data class SimulatorDataModel(
  val eventGroupName: String,
  val loadPerformanceName: String
)

class EventGroupSimulatorViewModel(
  eventGroupsRepository: EventGroupsRepository, private val simulatorDataModel: SimulatorDataModel,
  private val rankingScorePerformanceRepository: RankingScorePerformanceRepository
):ViewModel() {

  var isSnackBarBarShowing =  mutableStateOf(false)
    private set

  private var size:Int = 0

  var loadedRankingScorePerformanceData :RankingScorePerformanceData? = null

  init {
    viewModelScope.launch {
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
    title.postValue(performanceData.name)
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


  private var title = MutableLiveData("")
  fun getTitle(): LiveData<String> = title

  private var selectedEventGroup = MutableLiveData(
    eventGroupsRepository.getEventGroupByName(simulatorDataModel.eventGroupName)?: EventGroup.getSampleEventGroup()
  )
  fun getSelectedEventGroup() : LiveData<EventGroup> = selectedEventGroup

  private var listOfPerformances = MutableLiveData(
    initListOfDoubles()
  )
  fun getListOfPerformances() : LiveData<List<String>> = listOfPerformances

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
    initListOfEvents())
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

  private fun initListOfEvents(): List<AthleticsEvent> {
    size = selectedEventGroup.value!!.sMinNumberPerformancesGroup
    val array1 = arrayListOf<AthleticsEvent>()
    for (index in 1..size) {
      array1.add(selectedEventGroup.value!!.sMainEvent)
    }
    return array1
  }

  //SAVING PERFORMANCE

  fun savePerformance() {
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

  fun confirmSavePerformance() {
    viewModelScope.launch {
      savePerformanceToRepository()
    }
  }

  private suspend fun savePerformanceToRepository(performanceData: RankingScorePerformanceData? = null) {
    val rankingScore = performanceData ?: collectClassData()
    rankingScorePerformanceRepository.saveRankingScorePerformance(rankingScore)
    showSnackBar()
  }

  //UPDATING PERFORMANCE

  private suspend fun confirmUpdatePerformance(performanceData: RankingScorePerformanceData? = null) {
    val rankingScore = performanceData ?: collectClassData()
    rankingScorePerformanceRepository.updateRankingScorePerformance(rankingScore)
    showSnackBar()
  }

  //DELETE PERFORMANCE


  fun confirmDeletePerformance() {
    viewModelScope.launch {
      deletePerformanceFromRepo()
    }
  }

  private suspend fun deletePerformanceFromRepo() {
    viewModelScope.launch {
      loadedRankingScorePerformanceData?.let {
        rankingScorePerformanceRepository.deleteRankingScorePerformance(it)
      }
    }
  }

  //USER ACTIONS

  fun saveButtonPressed() =
    getFieldValidation().takeIf { it!=null }?.let { showDialogWithError() } ?: savePerformance()

  fun deleteButtonPressed() {
    showDeleteDialog.value=true
  }

  private fun showSnackBar() {
    isSnackBarBarShowing.value = true
  }

  fun hideDeleteDialog() {
    showDeleteDialog.value=false
  }

  fun hideNameOverwriteDialog() {
    showNameOverwriteDialog.value=false
  }

  private fun showDialogWithError() {
    TODO("Not yet implemented")
  }

  fun getFieldValidation():String? {
    //todo do an enum to check fields
    //do this stuff, then proceed with saving name override
    //for now it always passes, this could return the string message for the error in validation message
//    warningDialogWindowMessage.value = ""
      return null
  }

  //DATA & MODEL HANDLING

  private fun collectClassData(): RankingScorePerformanceData {
    return RankingScorePerformanceData(
      name = title.value!!,
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
    title.postValue(sTitle)
  }

  fun updatePerformancesList(index: Int, performance: String) {
    val performancesArrayValue = listOfPerformances.value!!.toMutableList()

    performancesArrayValue[index] = performance
    listOfPerformances.postValue(performancesArrayValue.toList())

    updatePerformancePointsList(index = index, performance = performance)
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
    updatePerformancePointsList(index = index, performance = listOfPerformances.value!![index], event = event)
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
    getAverage(total = perfPoints.toIntsArray().sum() + placementPoints.toIntsArray().sum() + windPoints.toIntsArray().sum(), size = size)


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