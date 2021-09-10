package com.example.athleticsrankingpoints.ui.eventgroupselector.simulator

import android.util.Log
import androidx.compose.runtime.State
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.athleticsrankingpoints.domain.EventGroup
import com.example.athleticsrankingpoints.repository.EventGroupsRepository
import kotlin.collections.ArrayList
import kotlin.random.Random

class EventGroupSimulatorViewModel(eventGroupsRepository: EventGroupsRepository, eventGroupName: String):ViewModel() {

  private var size:Int = 0

//  State<List<T>> and the immutable listOf()

  private var selectedEventGroup = MutableLiveData(eventGroupsRepository.getEventGroupByName(eventGroupName)?: EventGroup.getSampleEventGroup())
  fun getSelectedEventGroup() : LiveData<EventGroup> = selectedEventGroup

//  private var arrayOfPerformances = MutableLiveData(arrayListOf<String>())
  private var arrayOfPerformances = MutableLiveData(initArrayOfPerfs())
//  fun getListOfArrayOfPerformances() : LiveData<ArrayList<String>> = arrayOfPerformances
  fun getListOfArrayOfPerformances() : LiveData<List<String>> = arrayOfPerformances

  private fun initArrayOfPerfs(): List<String> {
    size = selectedEventGroup.value!!.sMinNumberPerformancesGroup
    val array1 = arrayListOf<String>()
    for (index in 1..size) {
      array1.add("0.0")
    }
    return array1
  }

//  private var arrayOfPoints = MutableLiveData(arrayListOf<String>())
  private var arrayOfPoints = MutableLiveData(initArrayOfPoints())
  fun getListOfArrayOfPoints() : LiveData<List<String>> = arrayOfPoints

  private fun initArrayOfPoints(): List<String> {
    size = selectedEventGroup.value!!.sMinNumberPerformancesGroup
    val array1 = arrayListOf<String>()
    for (index in 1..size) {
      array1.add("0")
    }
    return array1
  }

  fun updatePerformancesArray(index: Int, performance: String) {
    val pointsArrayValue = arrayOfPoints.value!!.toMutableList()
    val performancesArrayValue = arrayOfPerformances.value!!.toMutableList()

    performancesArrayValue[index] = performance
//    performancesArrayValue!![index] = performance
    arrayOfPerformances.postValue(performancesArrayValue.toList())

    pointsArrayValue[index] = kotlin.math.floor(performance.toDouble() * 3).toInt().toString() //do the actual points
    arrayOfPoints.postValue(pointsArrayValue.toList())
  }

  fun getPointsForIndex(index: Int): String {
    return ((3 + index)*34).toString()
  }


//  init {
//    selectedEventGroup.postValue(eventGroupsRepository.getEventGroupByName())
//  }

}