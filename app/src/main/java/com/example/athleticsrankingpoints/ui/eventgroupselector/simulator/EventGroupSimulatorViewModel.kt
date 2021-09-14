package com.example.athleticsrankingpoints.ui.eventgroupselector.simulator

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.athleticsrankingpoints.domain.AthleticsEvent
import com.example.athleticsrankingpoints.domain.EventGroup
import com.example.athleticsrankingpoints.repository.EventGroupsRepository
import java.lang.NumberFormatException
import kotlin.math.floor

class EventGroupSimulatorViewModel(eventGroupsRepository: EventGroupsRepository, eventGroupName: String):ViewModel() {

  private var size:Int = 0

  private var selectedEventGroup = MutableLiveData(eventGroupsRepository.getEventGroupByName(eventGroupName)?: EventGroup.getSampleEventGroup())
  fun getSelectedEventGroup() : LiveData<EventGroup> = selectedEventGroup

  private var listOfPerformances = MutableLiveData(initListOfPerfs())
  fun getListOfPerformances() : LiveData<List<String>> = listOfPerformances

  private var listOfPoints = MutableLiveData(initListOfPoints())
  fun getListOfPoints() : LiveData<List<String>> = listOfPoints

  private var listOfPlacementPoints = MutableLiveData(initListOfPoints())
  fun getListOfPlacementPoints() : LiveData<List<String>> = listOfPlacementPoints

  private var listOfSelectedEvents = MutableLiveData(initListOfEvents())
  fun getListOfSelectedEvents() : LiveData<List<AthleticsEvent>> = listOfSelectedEvents

  private var rankingScore = MutableLiveData("0")
  fun getRankingScore() : LiveData<String> = rankingScore

  private fun initListOfPerfs(): List<String> {
    size = selectedEventGroup.value!!.sMinNumberPerformancesGroup
    val array1 = arrayListOf<String>()
    for (index in 1..size) {
      array1.add("0.0")
    }
    return array1
  }


  private fun initListOfPoints(): List<String> {
    size = selectedEventGroup.value!!.sMinNumberPerformancesGroup
    val array1 = arrayListOf<String>()
    for (index in 1..size) {
      array1.add("0")
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

    updatePointsList(index = index, performance = performance)
  }

  fun updateSelectedEvent(index: Int, event: AthleticsEvent) {
    val selectedEventsValue = listOfSelectedEvents.value!!.toMutableList()
    selectedEventsValue[index] = event
    listOfSelectedEvents.postValue(selectedEventsValue.toList())
    updatePointsList(index = index, performance = listOfPerformances.value!![index], event = event)
  }

  private fun updatePointsList(index: Int, performance: String, event:AthleticsEvent? = null) {
    val pointsArrayValue = listOfPoints.value!!.toMutableList()
    pointsArrayValue[index] = getPointsForGivenIndex(numberPerformance = index, performance = performance, event = event) //do the actual points
    listOfPoints.postValue(pointsArrayValue.toList())
    updateRankingScore(performancePointsList = pointsArrayValue, placementPointsList = null)
  }

  fun updatePlacementPointsList(index: Int, points: String) {
    val placementPointsValue = listOfPlacementPoints.value!!.toMutableList()
    placementPointsValue[index] = points
    listOfPlacementPoints.postValue(placementPointsValue.toList())
    updateRankingScore(performancePointsList = null, placementPointsList = placementPointsValue)
  }

  private fun updateRankingScore(performancePointsList: List<String>? = null, placementPointsList: List<String>? = null) {

    var points1 = performancePointsList
    var points2 = placementPointsList

    if (points1==null) {
      points1 = listOfPoints.value!!
    }
    if (points2==null) {
      points2 = listOfPlacementPoints.value!!
    }

    var sum = 0
    for (index in points1.indices) {
      var score1 = 0
      var score2 = 0
      try {
        score1 = points1[index].toInt()
        score2 = points2[index].toInt()
      } catch (e:NumberFormatException) {
        Log.d("INT VALID?", "EXCEPTION IGNORED")
      }
      sum+=score1 + score2
    }
    rankingScore.postValue(getAverage(total = sum, size = size))
  }

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