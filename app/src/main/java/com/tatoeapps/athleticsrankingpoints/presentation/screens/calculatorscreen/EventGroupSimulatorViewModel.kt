package com.tatoeapps.athleticsrankingpoints.presentation.screens.calculatorscreen

import androidx.annotation.StringRes
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tatoeapps.athleticsrankingpoints.R
import com.tatoeapps.athleticsrankingpoints.data.entities.RankingScorePerformanceData
import com.tatoeapps.athleticsrankingpoints.domain.interfaces.CompetitionCategoryGroupRepository
import com.tatoeapps.athleticsrankingpoints.domain.interfaces.EventGroupsRepository
import com.tatoeapps.athleticsrankingpoints.domain.interfaces.RankingScorePerformanceRepository
import com.tatoeapps.athleticsrankingpoints.domain.models.*
import com.tatoeapps.athleticsrankingpoints.domain.models.AthleticsEvent.Companion.getSampleEvent
import com.tatoeapps.athleticsrankingpoints.domain.models.AthleticsEvent.Companion.getWindPoints
import com.tatoeapps.athleticsrankingpoints.toIntsArray
import kotlinx.coroutines.launch
import kotlin.math.floor

data class SimulatorDTO(
  val eventGroupName: String,
  val loadPerformanceName: String,
)

data class SnackBarModel(
  var isShowing: Boolean,
  var stringResId: Int?,
)

class EventGroupSimulatorViewModel(
  eventGroupsRepository: EventGroupsRepository, private val simulatorDTO: SimulatorDTO,
  private val rankingScorePerformanceRepository: RankingScorePerformanceRepository,
  private val competitionCategoryGroupsRepository: CompetitionCategoryGroupRepository,
) : ViewModel() {

  var snackBarModel = mutableStateOf(SnackBarModel(false, null))
    private set

  private var size: Int = 0

  var loadedRankingScorePerformanceData: RankingScorePerformanceData? = null

  private var selectedEventGroup = MutableLiveData(
    EventGroup.getSampleEventGroup()
  )

  fun getSelectedEventGroup(): LiveData<EventGroup> = selectedEventGroup

  val isNewEntry: Boolean
    get() = simulatorDTO.loadPerformanceName == RankingScorePerformanceData.NEW_ENTRY


  init {
    viewModelScope.launch {
      eventGroupsRepository.getEventGroupByName(simulatorDTO.eventGroupName)?.let {
        selectedEventGroup.value = it
        listOfSelectedEvents.value = initListOfEvents(it.sMainEvent, it.sMinNumberPerformancesGroup)
        listOfPerformances.value = initListOfPerformances(it.getAllEventsInGroup().first())
        competitionCategoryGroupsRepository.getCompetitionCategoryGroupByName(
          CompetitionCategoryEventGroup.getEnumFromEventGroupId(it.sWorldRankingCompetitionCategory)
        )?.let { ccg ->
          listOfPlacementPerformanceDetails.value = initListOfPlacements(ccg)
        }
      }
      if (!isNewEntry) {
        rankingScorePerformanceRepository.getRankingScorePerformanceByName(simulatorDTO.loadPerformanceName)?.let {
          loadedRankingScorePerformanceData = it
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
    listOfPlacementPerformanceDetails.postValue(performanceData.placementDetails)
    listOfSelectedEvents.postValue(performanceData.selectedEvents)
  }

  private var showDeleteDialog = MutableLiveData(
    false
  )

  fun getShowDeleteDialog(): LiveData<Boolean> = showDeleteDialog

  private var showNameOverwriteDialog = MutableLiveData(
    false
  )

  fun getShowNameOverwriteDialog(): LiveData<Boolean> = showNameOverwriteDialog

  private var showErrorValidatingDialog: MutableLiveData<ErrorDialogDTO> = MutableLiveData(
    ErrorDialogDTO()
  )

  fun getShowErrorValidatingDialog(): LiveData<ErrorDialogDTO> = showErrorValidatingDialog

  data class ErrorDialogDTO(val show: Boolean = false, val stringResId: Int? = null)


  private var scoreTitle = MutableLiveData("")
  fun getTitle(): LiveData<String> = scoreTitle

  private var titleValid = MutableLiveData(true)
  fun getIsTitleValid(): LiveData<Boolean> = titleValid

  private var isTitleInEditMode = MutableLiveData(isNewEntry)
  fun getIsTitleInEditMode(): LiveData<Boolean> = isTitleInEditMode

  private var listOfPerformances = MutableLiveData(
    initListOfPerformances()
  )

  fun getListOfPerformances(): LiveData<List<PerformanceUnitsAware>> = listOfPerformances

  private var listOfPerformancePoints = MutableLiveData(
    initListOfInts())

  fun getListOfPerformancePoints(): LiveData<List<String>> = listOfPerformancePoints

  private var listOfWinds = MutableLiveData(
    initListOfDoubles())

  fun getListOfWinds(): LiveData<List<String>> = listOfWinds

  private var listOfWindsPoints = MutableLiveData(
    initListOfInts())

  fun getListOfWindsPoints(): LiveData<List<String>> = listOfWindsPoints

  private var listOfPlacementPoints = MutableLiveData(
    initListOfInts())

  fun getListOfPlacementPoints(): LiveData<List<String>> = listOfPlacementPoints

  private var listOfPlacementPerformanceDetails = MutableLiveData(
    initListOfPlacements())

  fun getListOfPlacementPerformanceDetails(): LiveData<List<PerformancePlacementDetails>> = listOfPlacementPerformanceDetails

  private var listOfSelectedEvents = MutableLiveData(
    initListOfEvents(athleticsEvent = selectedEventGroup.value?.sMainEvent ?: getSampleEvent(), size = selectedEventGroup.value?.sMinNumberPerformancesGroup ?: 1))

  fun getListOfSelectedEvents(): LiveData<List<AthleticsEvent>> = listOfSelectedEvents

  private var rankingScore = MutableLiveData("0")
  fun getRankingScore(): LiveData<String> = rankingScore

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
    //todo fix the init here it inits with 100m
    val array1 = arrayListOf<AthleticsEvent>()
    for (index in 1..size) {
      array1.add(athleticsEvent)
    }
    return array1
  }

  private fun initListOfPlacements(competitionCategoryGroup: CompetitionCategoryGroup? = null): List<PerformancePlacementDetails> {
    size = selectedEventGroup.value!!.sMinNumberPerformancesGroup
    val array1 = arrayListOf<PerformancePlacementDetails>()
    for (index in 1..size) {
      array1.add(
        PerformancePlacementDetails(
          competitionCategoryGroup ?: CompetitionCategoryGroup.getDefault()
        )
      )
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
        if (this) {
          confirmSavePerformance()
        } else {
          showNameOverwriteDialog.value = true
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
    showSnackBar(R.string.toast_saved)
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
    showSnackBar(R.string.toast_updated)
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
    showSnackBar(R.string.toast_deleted)
  }

  //USER ACTIONS

  fun editTitlePressed() {
    if (isTitleInEditMode.value == true) {
      titleValid.postValue(scoreTitle.value?.length ?: 0 > 2)
    }
    isTitleInEditMode.postValue(!(isTitleInEditMode.value ?: true))
  }

  fun saveButtonPressed() =
    getFieldValidation().takeIf { it > 0 }?.let { showDialogWithError(it) } ?: savePerformance() //todo refactor this int id check

  fun deleteButtonPressed() {
    showDeleteDialog.value = true
  }

  private fun showSnackBar(@StringRes stringRes: Int) {
    snackBarModel.value = SnackBarModel(true, stringRes)
  }

  fun hideDeleteDialog() {
    showDeleteDialog.value = false
  }

  fun hideNameOverwriteDialog() {
    showNameOverwriteDialog.value = false
  }

  fun hideErrorValidateDialog() {
    showErrorValidatingDialog.value = showErrorValidatingDialog.value?.copy(show = false) ?: ErrorDialogDTO()
  }

  private fun showDialogWithError(dialogErrorStringResInt: Int) {
    showErrorValidatingDialog.postValue(ErrorDialogDTO(show = true, stringResId = dialogErrorStringResInt))
  }

  private fun getFieldValidation(): Int {
    var errorMessage = -1
    if ((scoreTitle.value?.length ?: 0) <= 2) {
      errorMessage = R.string.dialog_error_name_length
    }
    if (!isNumberPerformancesOfMainEventValid()) {
      errorMessage = R.string.dialog_error_min_event_performances
    }
    return errorMessage
  }

  private fun isNumberPerformancesOfMainEventValid(): Boolean {
    selectedEventGroup.value?.let { eventGroup ->
      var mainEventCounter = 0
      listOfSelectedEvents.value?.let {
        for (event in it) {
          if (event == eventGroup.sMainEvent) {
            mainEventCounter++
          }
        }
      }
      return mainEventCounter >= eventGroup.sMinNumberPerformancesMainEvent
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
      placementDetails = listOfPlacementPerformanceDetails.value!!,
      selectedEvents = listOfSelectedEvents.value!!,
      rankingScore = computeRankingScore()
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

  private fun updatePerformancePointsList(index: Int, performance: String, event: AthleticsEvent? = null) =
    listOfPerformancePoints.updateNewListValue(index, getPointsForGivenIndex(index, performance, event))

  private fun updateWindPointsList(index: Int, points: String) = listOfWindsPoints.updateNewListValue(index, points)

  fun updatePlacementPointsList(index: Int, points: String) = listOfPlacementPoints.updateNewListValue(index, points) //todo can be switched to updatePlacementDetailsList that already contains this info

  fun updatePlacementDetailsList(index: Int, placementDetails: PerformancePlacementDetails) = listOfPlacementPerformanceDetails.updateNewListValue(index, placementDetails)

  fun updateRankingScore() = rankingScore.postValue(computeRankingScore())

  private fun computeRankingScore(): String =
    getAverage(total = (
        (listOfPerformancePoints.value?.toIntsArray()?.sum() ?: 0)
            + (listOfPlacementPoints.value?.toIntsArray()?.sum() ?: 0)
            + (listOfWindsPoints.value?.toIntsArray()?.sum() ?: 0)
        ),
      size = listOfSelectedEvents.value?.size ?: 0)


  private fun getAverage(total: Int, size: Int): String {
    return floor(total.toDouble() / size).toInt().toString()
  }

  /**
   * If this is called by event change, event wont be null.
  This function uses the current livedata values so it wouldnt be safe with out doing it in this way.
   */
  private fun getPointsForGivenIndex(indexPerformance: Int, performance: String, event: AthleticsEvent? = null): String {
    return event?.getPointsString(performance = performance) ?: listOfSelectedEvents.value!![indexPerformance].getPointsString(performance = performance)
  }

}

fun <T> MutableLiveData<List<T>>.updateNewListValue(index: Int, newValueToInsert: T) {
  val newValue = this.value?.toMutableList()?.apply {
    this[index] = newValueToInsert
  } ?: emptyList()
  this.postValue(newValue)
}