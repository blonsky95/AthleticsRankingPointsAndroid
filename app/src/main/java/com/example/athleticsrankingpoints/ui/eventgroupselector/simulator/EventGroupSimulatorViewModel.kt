package com.example.athleticsrankingpoints.ui.eventgroupselector.simulator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.athleticsrankingpoints.domain.AthleticsEvent
import com.example.athleticsrankingpoints.domain.AthleticsEvent.Companion.getWindPoints
import com.example.athleticsrankingpoints.domain.EventGroup
import com.example.athleticsrankingpoints.repository.EventGroupsRepository
import com.example.athleticsrankingpoints.toIntsArray
import kotlin.math.floor

class EventGroupSimulatorViewModel(eventGroupsRepository: EventGroupsRepository, eventGroupName: String):ViewModel() {

  private var size:Int = 0

  private var selectedEventGroup = MutableLiveData(eventGroupsRepository.getEventGroupByName(eventGroupName)?: EventGroup.getSampleEventGroup())
  fun getSelectedEventGroup() : LiveData<EventGroup> = selectedEventGroup

  private var listOfPerformances = MutableLiveData(initListOfDoubles())
  fun getListOfPerformances() : LiveData<List<String>> = listOfPerformances

  private var listOfPerformancePoints = MutableLiveData(initListOfInts())
  fun getListOfPerformancePoints() : LiveData<List<String>> = listOfPerformancePoints

  private var listOfWinds = MutableLiveData(initListOfDoubles())
  fun getListOfWinds() : LiveData<List<String>> = listOfWinds

  private var listOfWindsPoints = MutableLiveData(initListOfInts())
  fun getListOfWindsPoints() : LiveData<List<String>> = listOfWindsPoints

  private var listOfPlacementPoints = MutableLiveData(initListOfInts())
  fun getListOfPlacementPoints() : LiveData<List<String>> = listOfPlacementPoints

  private var listOfSelectedEvents = MutableLiveData(initListOfEvents())
  fun getListOfSelectedEvents() : LiveData<List<AthleticsEvent>> = listOfSelectedEvents

  private var rankingScore = MutableLiveData("0")
  fun getRankingScore() : LiveData<String> = rankingScore

  private fun initListOfDoubles(): List<String> {
    size = selectedEventGroup.value!!.sMinNumberPerformancesGroup
    val array1 = arrayListOf<String>()
    for (index in 1..size) {
      array1.add("")
    }
    return array1
  }

  private fun initListOfInts(): List<String> {
    size = selectedEventGroup.value!!.sMinNumberPerformancesGroup
    val array1 = arrayListOf<String>()
    for (index in 1..size) {
      array1.add("")
    }
    return array1
  }

  private fun initListOfEvents(): List<AthleticsEvent> {
    size = selectedEventGroup.value!!.sMinNumberPerformancesGroup
    val array1 = arrayListOf<AthleticsEvent>()
    for (index in 1..size) {
      array1.add(selectedEventGroup.value!!.sMainEvent)
    }
    return array1
  }

  fun updatePerformancesList(index: Int, performance: String) {
    val performancesArrayValue = listOfPerformances.value!!.toMutableList()

    performancesArrayValue[index] = performance
    listOfPerformances.postValue(performancesArrayValue.toList())

    updatePerformancePointsList(index = index, performance = performance)
  }

  fun updateWindList(index: Int, wind: String) {
    val windsValue = listOfWinds.value!!.toMutableList()
    windsValue[index] = wind
    listOfWinds.postValue(windsValue.toList())
    updateWindPointsList(index, getWindPoints(wind).toString())
  }

  fun updateSelectedEvent(index: Int, event: AthleticsEvent) {
    val selectedEventsValue = listOfSelectedEvents.value!!.toMutableList()
    selectedEventsValue[index] = event
    listOfSelectedEvents.postValue(selectedEventsValue.toList())
    updatePerformancePointsList(index = index, performance = listOfPerformances.value!![index], event = event)
  }

  private fun updatePerformancePointsList(index: Int, performance: String, event:AthleticsEvent? = null) {
    val pointsPerfArrayValue = listOfPerformancePoints.value!!.toMutableList()
    pointsPerfArrayValue[index] = getPointsForGivenIndex(numberPerformance = index, performance = performance, event = event) //do the actual points
    listOfPerformancePoints.postValue(pointsPerfArrayValue.toList())
  }



  private fun updateWindPointsList(index: Int, points: String) {
    val pointsWindArrayValue = listOfWindsPoints.value!!.toMutableList()
    pointsWindArrayValue[index] = points
    listOfWindsPoints.postValue(pointsWindArrayValue.toList())
  }

  fun updatePlacementPointsList(index: Int, points: String) {
    val placementPointsValue = listOfPlacementPoints.value!!.toMutableList()
    placementPointsValue[index] = points
    listOfPlacementPoints.postValue(placementPointsValue.toList())
  }

  fun getRankingScore(perfPoints:List<String>, placementPoints:List<String>, windPoints:List<String>):String  =
    getAverage(total = perfPoints.toIntsArray().sum() + placementPoints.toIntsArray().sum() + windPoints.toIntsArray().sum(), size = size)


  private fun getAverage(total:Int, size:Int):String {
    return floor(total.toDouble()/size).toInt().toString()
  }

  /**
   * If this is called by event change, event wont be null.
  This function uses the current livedata values so it wouldnt be safe with out doing it in this way.
   */
  private fun getPointsForGivenIndex(numberPerformance: Int, performance: String, event:AthleticsEvent? = null): String {
    return event?.getPointsString(performance = performance) ?: listOfSelectedEvents.value!![numberPerformance].getPointsString(performance = performance)
  }



}