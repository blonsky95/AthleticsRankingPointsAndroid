package com.tatoeapps.athleticsrankingpoints.presentation.screens.homescreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tatoeapps.athleticsrankingpoints.domain.interfaces.AthleticsEventsRepository
import com.tatoeapps.athleticsrankingpoints.domain.models.*
import kotlinx.coroutines.launch
import kotlin.math.pow

class HomeViewModel(private val athleticsEventsRepository: AthleticsEventsRepository):ViewModel() {

  val sampleFirstEvent = AthleticsEvent.getSampleEvent()

  private var selectedEvent = MutableLiveData(sampleFirstEvent)
  fun getSelectedEvent() : LiveData<AthleticsEvent> = selectedEvent

  private var listOfEvents = MutableLiveData(listOf<AthleticsEvent>())
  fun getListOfEvents() : LiveData<List<AthleticsEvent>> = listOfEvents

  private var selectedSex = MutableLiveData(AthleticsSex.Male)
  fun getSelectedSex() : LiveData<AthleticsSex> = selectedSex

  private var selectedDoor = MutableLiveData(AthleticsDoor.Indoor)
  fun getSelectedDoor() : LiveData<AthleticsDoor> = selectedDoor

  private var performanceUnitsAware = MutableLiveData(
    PerformanceUnitsAware.getDefault()
  )

  fun getPerformanceUnitsAware() : LiveData<PerformanceUnitsAware> = performanceUnitsAware

  private var performancePoints = MutableLiveData("0")
  fun getPerformancePoints() : LiveData<String> = performancePoints



  init {
    viewModelScope.launch {
      updateEventList()
    }
  }

  fun newEventSelected(athleticsEvent: AthleticsEvent) {
    selectedEvent.postValue(athleticsEvent)
    updatePerformanceUnitsAware(
      PerformanceUnitsAware.getDefault(event = athleticsEvent)
    )
  }

  private fun updateEventList(selectedSex: AthleticsSex = AthleticsSex.Male, selectedDoor: AthleticsDoor = AthleticsDoor.Indoor) {
    getCategory(selectedSex,selectedDoor).let {
      viewModelScope.launch {
        listOfEvents.postValue(athleticsEventsRepository.getAthleticEventByCategory(it))
        newEventSelected(athleticsEventsRepository.getAthleticEventByCategory(it)[0])
      }
    }
  }

  private fun getCategory(selectedSex: AthleticsSex, selectedDoor: AthleticsDoor): AthleticsEventCategory {
    return if (selectedSex == AthleticsSex.Male) {
      if (selectedDoor == AthleticsDoor.Indoor) {
        AthleticsEventCategory.category_indoor_male
      } else {
        AthleticsEventCategory.category_outdoor_male
      }
    } else {
      if (selectedDoor == AthleticsDoor.Indoor) {
        AthleticsEventCategory.category_indoor_female
      } else {
        AthleticsEventCategory.category_outdoor_female
      }
    }
  }

  fun updateUIWithNewSexCategory(selection:AthleticsSex) {
    selectedSex.postValue(selection)
    updateEventList(selectedSex = selection, selectedDoor = selectedDoor.value!!)
  }

  fun updateUIWithNewDoorCategory(selection:AthleticsDoor) {
    selectedDoor.postValue(selection)
    updateEventList(selectedSex = selectedSex.value!!, selectedDoor = selection)
  }

  fun updatePerformanceUnitsAware(newPerformance: PerformanceUnitsAware){
    performanceUnitsAware.postValue(newPerformance)
    performancePoints.postValue(
      if (newPerformance.isPerformanceValid()) {
        selectedEvent.value!!.getPointsString(newPerformance.getPerformanceAbsoluteValue())
      } else {
        "Error"
      })
  }


fun getTotalFromList(list:List<String>):String {
  var total = 0.0
  for (n in list.indices) {
    total += (list[list.size-1-n].toDoubleOrNull() ?: 0.0) * (60.0.pow(n.toDouble()))
  }

  return total.toString()
}

}