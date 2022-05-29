package com.tatoeapps.athleticsrankingpoints.data.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.beust.klaxon.Klaxon
import com.tatoeapps.athleticsrankingpoints.data.database.RankingScoreDatabaseDao
import com.tatoeapps.athleticsrankingpoints.di.allCompetitionCategoryGroupsJsonFileName
import com.tatoeapps.athleticsrankingpoints.di.allEventGroupsJsonFileName
import com.tatoeapps.athleticsrankingpoints.domain.interfaces.CompetitionCategoryGroupRepository
import com.tatoeapps.athleticsrankingpoints.domain.models.EventGroup
import com.tatoeapps.athleticsrankingpoints.domain.interfaces.EventGroupsRepository
import com.tatoeapps.athleticsrankingpoints.domain.models.AthleticsSex
import com.tatoeapps.athleticsrankingpoints.domain.models.CompetitionCategoryEventGroup
import com.tatoeapps.athleticsrankingpoints.domain.models.CompetitionCategoryGroup
import kotlinx.coroutines.runBlocking

class CompetitionCategoryGroupsRepositoryImpl(applicationContext: Context, private val rankingScoreDatabaseDao: RankingScoreDatabaseDao) : CompetitionCategoryGroupRepository {

  private val _isLoading = MutableLiveData(true)

  private val jsonFileName: String = allCompetitionCategoryGroupsJsonFileName

  private var cacheListOfGroups:List<CompetitionCategoryGroup> = mutableListOf()

  init {
    Log.d("CACHE", "initCompetitionCategoryGroup repo")

    runBlocking {
      if (!roomDatabaseExists()) {
        Log.d("CACHE", "creating room")

        val jsonFile: String = applicationContext.assets.open(jsonFileName).bufferedReader().use { it.readText() }
        val result = Klaxon().parseArray<CompetitionCategoryGroup>(jsonFile)
        result?.let {
          insertAllCompetitionCategoryGroups(result)
        }
      }
      _isLoading.postValue(false)
    }
    Log.d("CACHE", "finish loading event groups repo")

  }

  private suspend fun roomDatabaseExists(): Boolean {
    return rankingScoreDatabaseDao.getFirstCompetitionCategoryGroup()!=null
  }

  private fun getCachedCategoryList():List<CompetitionCategoryGroup> =
    cacheListOfGroups


  override suspend fun getAllCompetitionCategoryGroups(): List<CompetitionCategoryGroup> {
    return getCachedCategoryList().takeIf { it.isNotEmpty() } ?: rankingScoreDatabaseDao.getAllCompetitionCategoryGroups().apply {
      cacheListOfGroups=this
    }
  }

  override suspend fun getCompetitionCategoryGroupByName(name: CompetitionCategoryEventGroup): CompetitionCategoryGroup? {
    return rankingScoreDatabaseDao.getCompetitionCategoryGroupByName(name.eventGroupId)
  }

  override suspend fun insertAllCompetitionCategoryGroups(list: List<CompetitionCategoryGroup>) {
    rankingScoreDatabaseDao.insertAllCompetitionCategoryGroups(list)
  }

  override fun getIsRepositoryLoading(): LiveData<Boolean> = _isLoading
}