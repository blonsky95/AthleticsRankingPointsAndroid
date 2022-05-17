package com.tatoeapps.athleticsrankingpoints.domain.interfaces

import androidx.lifecycle.LiveData
import com.tatoeapps.athleticsrankingpoints.domain.models.AthleticsSex
import com.tatoeapps.athleticsrankingpoints.domain.models.EventGroup

interface EventGroupsRepository {

  suspend fun getAllEventGroups():List<EventGroup>

  suspend fun getEventGroupsBySex(sex: AthleticsSex):List<EventGroup>

  suspend fun getEventGroupByName(name: String) : EventGroup?

  suspend fun insertAllEventGroups(list:List<EventGroup>)

  fun getIsRepositoryLoading(): LiveData<Boolean>

}
