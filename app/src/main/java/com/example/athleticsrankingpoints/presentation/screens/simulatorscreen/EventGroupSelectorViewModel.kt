package com.example.athleticsrankingpoints.presentation.screens.simulatorscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athleticsrankingpoints.domain.models.EventGroup
import com.example.athleticsrankingpoints.domain.interfaces.EventGroupsRepository
import com.example.athleticsrankingpoints.domain.models.AthleticsEventSex
import kotlinx.coroutines.launch

class EventGroupSelectorViewModel(private val eventGroupsRepository: EventGroupsRepository):ViewModel() {

  val sampleFirstEventGroup = EventGroup.getSampleEventGroup()

  private var selectedEventGroup = MutableLiveData(sampleFirstEventGroup)
  fun getSelectedEventGroup() : LiveData<EventGroup> = selectedEventGroup

  private var listOfEvents = MutableLiveData(listOf<EventGroup>())
  fun getListOfEventGroups() : LiveData<List<EventGroup>> = listOfEvents

  private var selectedSex = MutableLiveData(AthleticsEventSex.Male)
  fun getSelectedSex() : LiveData<AthleticsEventSex> = selectedSex

  var fullListOfEventGroups = listOf<EventGroup>()

  init {
    viewModelScope.launch {
      fullListOfEventGroups = eventGroupsRepository.getAllEventGroups()
      updateEventList()
    }
  }

  fun newEventSelected(eventGroup: EventGroup) {
    selectedEventGroup.postValue(eventGroup)
  }

  private fun updateEventList(selectedSex:AthleticsEventSex = AthleticsEventSex.Male) {
    listOfEvents.postValue(eventGroupsRepository.getEventGroupsBySex(selectedSex))
    newEventSelected(eventGroupsRepository.getEventGroupsBySex(selectedSex)[0])
  }

  fun updateUIWithNewSexCategory(selection:AthleticsEventSex) {
    selectedSex.postValue(selection)
    updateEventList(selectedSex = selection)
  }


}