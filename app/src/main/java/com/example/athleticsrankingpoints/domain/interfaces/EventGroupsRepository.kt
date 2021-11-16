package com.example.athleticsrankingpoints.domain.interfaces

import com.example.athleticsrankingpoints.domain.AthleticsEvent
import com.example.athleticsrankingpoints.domain.EventGroup

interface EventGroupsRepository {

  val jsonFileName:String

  suspend fun getAllEventGroups():List<EventGroup>

  fun getEventGroupsBySex(category: String):List<EventGroup>

  fun getEventGroupByName(eventGroupName: String) : EventGroup?

}