package com.example.athleticsrankingpoints.ui.lookupscreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athleticsrankingpoints.domain.AthleticsEvent
import com.example.athleticsrankingpoints.repository.AthleticsEventsRepository
import kotlinx.coroutines.launch

class LookUpViewModel(private val athleticsEventsRepository: AthleticsEventsRepository):ViewModel() {

  val sampleFirstEvent = AthleticsEvent.getSampleEvent()

  private var selectedEvent = MutableLiveData(sampleFirstEvent)
  fun getSelectedEvent() : LiveData<AthleticsEvent> = selectedEvent

  private var listOfEvents = MutableLiveData(listOf<AthleticsEvent>())
  fun getListOfEvents() : LiveData<List<AthleticsEvent>> = listOfEvents

  private var selectedSex = MutableLiveData(AthleticsEvent.sexMale)
  fun getSelectedSex() : LiveData<String> = selectedSex

  private var selectedDoor = MutableLiveData(AthleticsEvent.doorIndoor)
  fun getSelectedDoor() : LiveData<String> = selectedDoor

  private var performanceString = MutableLiveData("0.0")
  fun getPerformanceString() : LiveData<String> = performanceString

  private var performancePoints = MutableLiveData("0")
  fun getPerformancePoints() : LiveData<String> = performancePoints


  var fullListOfEvents = listOf<AthleticsEvent>()

  init {
    Log.d("LOOK UP VIEW MODEL ", "WAS INIT")
    viewModelScope.launch {
      fullListOfEvents = athleticsEventsRepository.getAllAthleticsEvents()
      updateEventList()
    }
  }

  fun newEventSelected(athleticsEvent: AthleticsEvent) {
    selectedEvent.postValue(athleticsEvent)
    updatePerformanceString("0.0")
  }

  private fun updateEventList(selectedSex:String = AthleticsEvent.sexMale, selectedDoor:String =AthleticsEvent.doorIndoor) {
    Log.d("Update event list check", "This would call the list for $selectedSex $selectedDoor")
    val category:String = buildCategoryWord(selectedSex,selectedDoor)
    listOfEvents.postValue(athleticsEventsRepository.getAthleticEventsByCategory(category = category))
    newEventSelected(athleticsEventsRepository.getAthleticEventsByCategory(category = category)[0])
  }

  private fun buildCategoryWord(selectedSex: String, selectedDoor: String): String {
    return if (selectedSex == AthleticsEvent.sexMale) {
      if (selectedDoor == AthleticsEvent.doorIndoor) {
        AthleticsEvent.categoryIndoorMale
      } else {
        AthleticsEvent.categoryOutdoorMale
      }
    } else {
      if (selectedDoor == AthleticsEvent.doorIndoor) {
        AthleticsEvent.categoryIndoorFemale
      } else {
        AthleticsEvent.categoryOutdoorFemale
      }
    }
  }

  fun updateUIWithNewSexCategory(selection:String) {
    selectedSex.postValue(selection)
    updateEventList(selectedSex = selection, selectedDoor = selectedDoor.value!!)
  }

  fun updateUIWithNewDoorCategory(selection:String) {
    selectedDoor.postValue(selection)
    updateEventList(selectedSex = selectedSex.value!!, selectedDoor = selection)
  }

  fun updatePerformanceString(newPerformance: String){
    performanceString.postValue(newPerformance)
    performancePoints.postValue(selectedEvent.value!!.getPointsString(newPerformance))
  }
}