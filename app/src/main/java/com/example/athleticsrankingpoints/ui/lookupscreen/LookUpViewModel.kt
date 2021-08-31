package com.example.athleticsrankingpoints.ui.lookupscreen

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

  fun newEventSelected(athleticsEvent: AthleticsEvent) {
    selectedEvent.postValue(athleticsEvent)
  }

  fun resetSelection() {
    //TODO will change
    selectedEvent.postValue(sampleFirstEvent)
  }

  fun updateEventList(selectedSex:String = AthleticsEvent.sexMale, selectedDoor:String =AthleticsEvent.doorIndoor) {
    //Here you would actually fill the list with the respective category list

    //might not need params if selected sex and door are in the view model

    listOfEvents.postValue(AthleticsEvent.getLongerEventsList())
  }

}