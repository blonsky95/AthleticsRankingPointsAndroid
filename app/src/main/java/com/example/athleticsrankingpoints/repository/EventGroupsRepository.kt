package com.example.athleticsrankingpoints.repository

import com.example.athleticsrankingpoints.domain.AthleticsEvent
import com.example.athleticsrankingpoints.domain.EventGroup

interface EventGroupsRepository {

  val jsonFileName:String

  suspend fun getAllEventGroups():List<EventGroup>

  fun getEventGroupsBySex(category: String):List<EventGroup>

  fun getEventGroupByName(eventGroupName: String) : EventGroup?

}