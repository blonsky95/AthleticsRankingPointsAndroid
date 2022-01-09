package com.example.athleticsrankingpoints.data.repositories

import android.content.Context
import com.beust.klaxon.Klaxon
import com.example.athleticsrankingpoints.di.allEventsJsonFileName
import com.example.athleticsrankingpoints.domain.models.AthleticsEvent
import com.example.athleticsrankingpoints.domain.interfaces.AthleticsEventsRepository

//There will be another repo to save stuff in the simulatr tab
class AthleticsEventsRepository(applicationContext: Context) : AthleticsEventsRepository {

  private val jsonFileName: String = allEventsJsonFileName

  var listOfAllEvents:List<AthleticsEvent> = listOf()
  var listOfMaleIndoorEvents:MutableList<AthleticsEvent> = mutableListOf()
  var listOfFemaleIndoorEvents:MutableList<AthleticsEvent> = mutableListOf()
  var listOfMaleOutdoorEvents:MutableList<AthleticsEvent> = mutableListOf()
  var listOfFemaleOutdoorEvents:MutableList<AthleticsEvent> = mutableListOf()

  init {
    val jsonFile: String = applicationContext.assets.open(jsonFileName).bufferedReader().use {it.readText()}
    val result = Klaxon().parseArray<AthleticsEvent>(jsonFile)
    if (result!=null){
      listOfAllEvents = result
      filterListByCategories(listOfAllEvents)
      //filter list
    }
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