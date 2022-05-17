package com.tatoeapps.athleticsrankingpoints.presentation.screens.calculatorscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tatoeapps.athleticsrankingpoints.domain.models.EventGroup
import com.tatoeapps.athleticsrankingpoints.domain.interfaces.EventGroupsRepository
import com.tatoeapps.athleticsrankingpoints.domain.models.AthleticsSex
import kotlinx.coroutines.launch

class EventGroupSelectorViewModel(private val eventGroupsRepository: EventGroupsRepository):ViewModel() {

  val sampleFirstEventGroup = EventGroup.getSampleEventGroup()

  private var selectedEventGroup = MutableLiveData(sampleFirstEventGroup)
  fun getSelectedEventGroup() : LiveData<EventGroup> = selectedEventGroup

  private var listOfEvents = MutableLiveData(listOf<EventGroup>())
  fun getListOfEventGroups() : LiveData<List<EventGroup>> = listOfEvents

  private var selectedSex = MutableLiveData(AthleticsSex.Male)
  fun getSelectedSex() : LiveData<AthleticsSex> = selectedSex


  init {
    viewModelScope.launch {
      updateEventList()
    }
  }

  fun newEventSelected(eventGroup: EventGroup) {
    selectedEventGroup.postValue(eventGroup)
  }

  private fun updateEventList(selectedSex:AthleticsSex = AthleticsSex.Male) = viewModelScope.launch {
    listOfEvents.postValue(eventGroupsRepository.getEventGroupsBySex(selectedSex))
    newEventSelected(eventGroupsRepository.getEventGroupsBySex(selectedSex)[0])
  }

  fun updateUIWithNewSexCategory(selection:AthleticsSex) {
    selectedSex.postValue(selection)
    updateEventList(selectedSex = selection)
  }


}