package com.example.athleticsrankingpoints.presentation.screens.lookupscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athleticsrankingpoints.domain.models.AthleticsEvent
import com.example.athleticsrankingpoints.domain.interfaces.AthleticsEventsRepository
import com.example.athleticsrankingpoints.domain.models.AthleticsEventCategory
import com.example.athleticsrankingpoints.domain.models.AthleticsEventDoor
import com.example.athleticsrankingpoints.domain.models.AthleticsEventSex
import kotlinx.coroutines.launch

class LookUpViewModel(private val athleticsEventsRepository: AthleticsEventsRepository):ViewModel() {

  val sampleFirstEvent = AthleticsEvent.getSampleEvent()

  private var selectedEvent = MutableLiveData(sampleFirstEvent)
  fun getSelectedEvent() : LiveData<AthleticsEvent> = selectedEvent

  private var listOfEvents = MutableLiveData(listOf<AthleticsEvent>())
  fun getListOfEvents() : LiveData<List<AthleticsEvent>> = listOfEvents

  private var selectedSex = MutableLiveData(AthleticsEventSex.Male)
  fun getSelectedSex() : LiveData<AthleticsEventSex> = selectedSex

  private var selectedDoor = MutableLiveData(AthleticsEventDoor.Indoor)
  fun getSelectedDoor() : LiveData<AthleticsEventDoor> = selectedDoor

  private var performanceString = MutableLiveData("0.0")
  fun getPerformanceString() : LiveData<String> = performanceString

  private var performancePoints = MutableLiveData("0")
  fun getPerformancePoints() : LiveData<String> = performancePoints


//  var fullListOfEvents = listOf<AthleticsEvent>()

  init {
    viewModelScope.launch {
//      fullListOfEvents = athleticsEventsRepository.getAllAthleticsEvents()
      updateEventList()
    }
  }

  fun newEventSelected(athleticsEvent: AthleticsEvent) {
    selectedEvent.postValue(athleticsEvent)
    updatePerformanceString("0.0")
  }

  private fun updateEventList(selectedSex: AthleticsEventSex = AthleticsEventSex.Male, selectedDoor: AthleticsEventDoor = AthleticsEventDoor.Indoor) {
    getCategory(selectedSex,selectedDoor).let {
      viewModelScope.launch {
        listOfEvents.postValue(athleticsEventsRepository.getAthleticEventByCategory(it))
        newEventSelected(athleticsEventsRepository.getAthleticEventByCategory(it)[0])
      }
    }
  }

  private fun getCategory(selectedSex: AthleticsEventSex, selectedDoor: AthleticsEventDoor): AthleticsEventCategory {
    return if (selectedSex == AthleticsEventSex.Male) {
      if (selectedDoor == AthleticsEventDoor.Indoor) {
        AthleticsEventCategory.category_indoor_male
      } else {
        AthleticsEventCategory.category_outdoor_male
      }
    } else {
      if (selectedDoor == AthleticsEventDoor.Indoor) {
        AthleticsEventCategory.category_indoor_female
      } else {
        AthleticsEventCategory.category_outdoor_female
      }
    }
  }

  fun updateUIWithNewSexCategory(selection:AthleticsEventSex) {
    selectedSex.postValue(selection)
    updateEventList(selectedSex = selection, selectedDoor = selectedDoor.value!!)
  }

  fun updateUIWithNewDoorCategory(selection:AthleticsEventDoor) {
    selectedDoor.postValue(selection)
    updateEventList(selectedSex = selectedSex.value!!, selectedDoor = selection)
  }

  fun updatePerformanceString(newPerformance: String){
    performanceString.postValue(newPerformance)
    performancePoints.postValue(selectedEvent.value!!.getPointsString(newPerformance))
  }
}