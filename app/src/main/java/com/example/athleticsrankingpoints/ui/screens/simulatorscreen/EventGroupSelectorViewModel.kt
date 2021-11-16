package com.example.athleticsrankingpoints.ui.screens.simulatorscreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athleticsrankingpoints.domain.AthleticsEvent
import com.example.athleticsrankingpoints.domain.EventGroup
import com.example.athleticsrankingpoints.domain.interfaces.EventGroupsRepository
import kotlinx.coroutines.launch

class EventGroupSelectorViewModel(private val eventGroupsRepository: EventGroupsRepository):ViewModel() {

  val sampleFirstEventGroup = EventGroup.getSampleEventGroup()

  private var selectedEventGroup = MutableLiveData(sampleFirstEventGroup)
  fun getSelectedEventGroup() : LiveData<EventGroup> = selectedEventGroup

  private var listOfEvents = MutableLiveData(listOf<EventGroup>())
  fun getListOfEventGroups() : LiveData<List<EventGroup>> = listOfEvents

  private var selectedSex = MutableLiveData(AthleticsEvent.sexMale)
  fun getSelectedSex() : LiveData<String> = selectedSex

  var fullListOfEventGroups = listOf<EventGroup>()

  init {
    Log.d("EVENT GROUP VIEW MODEL ", "WAS INIT")
    viewModelScope.launch {
      fullListOfEventGroups = eventGroupsRepository.getAllEventGroups()
      updateEventList()
    }
  }

  fun newEventSelected(eventGroup: EventGroup) {
    selectedEventGroup.postValue(eventGroup)
  }

  private fun updateEventList(selectedSex:String = EventGroup.sSexMale) {
    listOfEvents.postValue(eventGroupsRepository.getEventGroupsBySex(selectedSex))
    newEventSelected(eventGroupsRepository.getEventGroupsBySex(selectedSex)[0])
  }

  fun updateUIWithNewSexCategory(selection:String) {
    selectedSex.postValue(selection)
    updateEventList(selectedSex = selection)
  }


}