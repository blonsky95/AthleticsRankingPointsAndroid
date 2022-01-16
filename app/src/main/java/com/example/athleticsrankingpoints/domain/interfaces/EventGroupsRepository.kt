package com.example.athleticsrankingpoints.domain.interfaces

import com.example.athleticsrankingpoints.domain.models.AthleticsEventSex
import com.example.athleticsrankingpoints.domain.models.EventGroup

interface EventGroupsRepository {

  val jsonFileName:String

  suspend fun getAllEventGroups():List<EventGroup>

  fun getEventGroupsBySex(sex: AthleticsEventSex):List<EventGroup>

  fun getEventGroupByName(eventGroupName: String) : EventGroup?

}