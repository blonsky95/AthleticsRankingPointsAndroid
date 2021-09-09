package com.example.athleticsrankingpoints.ui.eventgroupselector.simulator

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athleticsrankingpoints.domain.AthleticsEvent
import com.example.athleticsrankingpoints.domain.EventGroup
import com.example.athleticsrankingpoints.repository.AthleticsEventsRepository
import com.example.athleticsrankingpoints.repository.EventGroupsRepository
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class EventGroupSimulatorViewModel(private val eventGroupsRepository: EventGroupsRepository):ViewModel() {

  private lateinit var selectedEventGroup:EventGroup
  private var size:Int = 0
  val perfInitialValue = "0.0"
  val pointsInitialValue = "0"


  fun getEventGroup(eventGroupName: String): EventGroup {
    selectedEventGroup = eventGroupsRepository.getEventGroupByName(eventGroupName)?: EventGroup.getSampleEventGroup()
    initVariables()
    return selectedEventGroup
  }

  private fun initVariables() {
    size = selectedEventGroup.sMinNumberPerformancesGroup
    val array1 = arrayListOf<String>()
    val array2 = arrayListOf<String>()

    for (index in 0..size) {
      array1.add("0.0")
      array2.add("0")
    }

    arrayOfPerformances.postValue(array1)
    arrayOfPoints.postValue(array2)
  }


  private var arrayOfPerformances = MutableLiveData(arrayListOf<String>())
  fun getListOfArrayOfPerformances() : LiveData<ArrayList<String>> = arrayOfPerformances

  private var arrayOfPoints = MutableLiveData(arrayListOf<String>())
  fun getListOfArrayOfPoints() : LiveData<ArrayList<String>> = arrayOfPoints




//  init {
//    selectedEventGroup.postValue(eventGroupsRepository.getEventGroupByName())
//  }

}