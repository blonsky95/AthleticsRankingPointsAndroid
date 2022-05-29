package com.tatoeapps.athleticsrankingpoints.domain.interfaces

import androidx.lifecycle.LiveData
import com.tatoeapps.athleticsrankingpoints.domain.models.AthleticsSex
import com.tatoeapps.athleticsrankingpoints.domain.models.CompetitionCategoryEventGroup
import com.tatoeapps.athleticsrankingpoints.domain.models.CompetitionCategoryGroup
import com.tatoeapps.athleticsrankingpoints.domain.models.EventGroup

interface CompetitionCategoryGroupRepository {

  suspend fun getAllCompetitionCategoryGroups():List<CompetitionCategoryGroup>

  suspend fun getCompetitionCategoryGroupByName(name: CompetitionCategoryEventGroup) : CompetitionCategoryGroup?

  suspend fun insertAllCompetitionCategoryGroups(list:List<CompetitionCategoryGroup>)

  fun getIsRepositoryLoading(): LiveData<Boolean>

}
