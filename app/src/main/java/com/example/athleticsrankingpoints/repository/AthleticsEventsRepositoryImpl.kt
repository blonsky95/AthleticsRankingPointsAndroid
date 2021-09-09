package com.example.athleticsrankingpoints.repository

import android.content.Context
import android.util.Log
import com.beust.klaxon.Klaxon
import com.example.athleticsrankingpoints.domain.AthleticsEvent

//There will be another repo to save stuff in the simulatr tab
class AthleticsEventsRepositoryImpl(applicationContext: Context, override val jsonFileName: String) : AthleticsEventsRepository{

  var listOfAllEvents:List<AthleticsEvent> = listOf()
  var listOfMaleIndoorEvents:MutableList<AthleticsEvent> = mutableListOf()
  var listOfFemaleIndoorEvents:MutableList<AthleticsEvent> = mutableListOf()
  var listOfMaleOutdoorEvents:MutableList<AthleticsEvent> = mutableListOf()
  var listOfFemaleOutdoorEvents:MutableList<AthleticsEvent> = mutableListOf()

  init {
    Log.d("LOOK UP REPO", "WAS INIT")
    val jsonFile: String = applicationContext.assets.open(jsonFileName).bufferedReader().use {it.readText()}
    val result = Klaxon().parseArray<AthleticsEvent>(jsonFile)
    if (result!=null){
      listOfAllEvents = result
      filterListByCategories(listOfAllEvents)
      //filter list
    }
    Log.d("LOOK UP REPO", "WAS FINITO LOADING")

  }

  private fun filterListByCategories(list: List<AthleticsEvent>) {
    for (event in list) {
      when (event.sCategory) {
        AthleticsEvent.categoryIndoorMale -> listOfMaleIndoorEvents.add(event)
        AthleticsEvent.categoryIndoorFemale -> listOfFemaleIndoorEvents.add(event)
        AthleticsEvent.categoryOutdoorMale -> listOfMaleOutdoorEvents.add(event)
        AthleticsEvent.categoryOutdoorFemale -> listOfFemaleOutdoorEvents.add(event)
      }
    }
  }

  //use the json file to get the data
  override suspend fun getAllAthleticsEvents(): List<AthleticsEvent> {
    return listOfAllEvents
  }

  override fun getAthleticEventsByCategory(category: String): List<AthleticsEvent> {
    return when (category) {
      AthleticsEvent.categoryIndoorMale -> listOfMaleIndoorEvents
      AthleticsEvent.categoryIndoorFemale -> listOfFemaleIndoorEvents
      AthleticsEvent.categoryOutdoorMale -> listOfMaleOutdoorEvents
      AthleticsEvent.categoryOutdoorFemale -> listOfFemaleOutdoorEvents
      else -> listOf()
    }
  }

  override suspend fun getAthleticEventByKey(key: String): AthleticsEvent {
    for (event in listOfAllEvents) {
      if (event.sKey==key) {
        return event
      }
    }
    //TODO make this better
    return AthleticsEvent.getSampleEvent()
  }
}