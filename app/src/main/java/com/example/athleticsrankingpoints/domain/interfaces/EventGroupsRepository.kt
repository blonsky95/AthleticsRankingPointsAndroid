package com.example.athleticsrankingpoints.domain.interfaces

import androidx.lifecycle.LiveData
import com.example.athleticsrankingpoints.domain.models.AthleticsEvent
import com.example.athleticsrankingpoints.domain.models.AthleticsEventCategory
import com.example.athleticsrankingpoints.domain.models.AthleticsSex
import com.example.athleticsrankingpoints.domain.models.EventGroup

interface EventGroupsRepository {

  suspend fun getAllEventGroups():List<EventGroup>

  suspend fun getEventGroupsBySex(sex: AthleticsSex):List<EventGroup>

  suspend fun getEventGroupByName(name: String) : EventGroup?

  suspend fun insertAllEventGroups(list:List<EventGroup>)

  fun getIsRepositoryLoading(): LiveData<Boolean>

}
