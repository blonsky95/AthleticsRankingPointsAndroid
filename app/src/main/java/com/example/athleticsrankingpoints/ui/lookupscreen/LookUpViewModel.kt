package com.example.athleticsrankingpoints.ui.lookupscreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.athleticsrankingpoints.domain.AthleticsEvent

class LookUpViewModel:ViewModel() {

  val sampleFirstEvent = AthleticsEvent.getSampleEvent()
  val sampleEventList = AthleticsEvent.getLongerEventsList()

  private var selectedEvent = MutableLiveData(sampleFirstEvent)
  fun getSelectedEvent() : LiveData<AthleticsEvent> = selectedEvent

  private var listOfEvents = MutableLiveData(AthleticsEvent.getLongerEventsList())
  fun getListOfEvents() : LiveData<List<AthleticsEvent>> = listOfEvents

  private var selectedSex = MutableLiveData(AthleticsEvent.sexMale)
  fun getSelectedSex() : LiveData<String> = selectedSex

  private var selectedDoor = MutableLiveData(AthleticsEvent.doorIndoor)
  fun getSelectedDoor() : LiveData<String> = selectedDoor

  private var performanceString = MutableLiveData("0.0")
  fun getPerformanceString() : LiveData<String> = performanceString

  private var performancePoints = MutableLiveData("0")
  fun getPerformancePoints() : LiveData<String> = performancePoints

  fun newEventSelected(athleticsEvent: AthleticsEvent) {
    selectedEvent.postValue(athleticsEvent)
    updatePerformanceString("0.0")
  }

  fun resetEventSelected() {
    newEventSelected(listOfEvents.value!![0])
  }

  fun updateEventList(selectedSex:String = AthleticsEvent.sexMale, selectedDoor:String =AthleticsEvent.doorIndoor) {
    //Here you would actually fill the list with the respective category list

    Log.d("Update event list check", "This would call the list for $selectedSex $selectedDoor")
    listOfEvents.postValue(AthleticsEvent.getLongerEventsList())
  }

  fun updateUIWithNewSexCategory(selection:String) {
    selectedSex.postValue(selection)
    updateEventList(selectedSex = selection, selectedDoor = selectedDoor.value!!)
    resetEventSelected()
  }

fun updateUIWithNewDoorCategory(selection:String) {
    selectedDoor.postValue(selection)
    updateEventList(selectedSex = selectedSex.value!!, selectedDoor = selection)
    resetEventSelected()
  }

  fun updatePerformanceString(newPerformance: String){
    performanceString.postValue(newPerformance)

    performancePoints.postValue(selectedEvent.value!!.getPointsString(newPerformance))

    //do logic to update performancePoints
  }


  //Athletics Event should have the logic to calculate points, so the below code will be added




}