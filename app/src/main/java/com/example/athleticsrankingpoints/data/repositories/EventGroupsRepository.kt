package com.example.athleticsrankingpoints.data.repositories

import android.content.Context
import android.util.Log
import com.beust.klaxon.Klaxon
import com.example.athleticsrankingpoints.domain.models.EventGroup
import com.example.athleticsrankingpoints.domain.interfaces.EventGroupsRepository

//There will be another repo to save stuff in the simulatr tab
class EventGroupsRepository(applicationContext: Context, override val jsonFileName: String) : EventGroupsRepository {

  var listOfAllEventGroups:List<EventGroup> = listOf()
  var listOfMaleGroups:MutableList<EventGroup> = mutableListOf()
  var listOfFemaleGroups:MutableList<EventGroup> = mutableListOf()

  init {
    Log.d("LOOK UP REPO", "WAS INIT")
    val jsonFile: String = applicationContext.assets.open(jsonFileName).bufferedReader().use {it.readText()}
    val result = Klaxon().parseArray<EventGroup>(jsonFile)
    if (result!=null){
      listOfAllEventGroups = result
      filterListBySex(listOfAllEventGroups)
      //filter list
    }
    Log.d("LOOK UP REPO", "WAS FINITO LOADING")

  }

  private fun filterListBySex(list: List<EventGroup>) {
    for (event in list) {
      when (event.sSex) {
        EventGroup.sSexMale -> listOfMaleGroups.add(event)
        EventGroup.sSexFemale -> listOfFemaleGroups.add(event)
      }
    }
  }

  override suspend fun getAllEventGroups(): List<EventGroup> {
    return listOfAllEventGroups
  }

  override fun getEventGroupsBySex(sex: String): List<EventGroup> {
    //Little modification, the sex identifier in event group is not the same as AthleticsEvent, "male" vs "Male
    return when (sex.lowercase()) {
      EventGroup.sSexMale -> listOfMaleGroups
      EventGroup.sSexFemale -> listOfFemaleGroups
      else -> listOf()
    }  }

  override fun getEventGroupByName(eventGroupName: String): EventGroup? {
    for (eGroup in listOfAllEventGroups) {
      if (eGroup.sName==eventGroupName) {
        return eGroup
      }
    }
    return null
  }
}