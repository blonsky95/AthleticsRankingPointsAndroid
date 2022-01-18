package com.example.athleticsrankingpoints.data.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.beust.klaxon.Klaxon
import com.example.athleticsrankingpoints.data.database.RankingScoreDatabaseDao
import com.example.athleticsrankingpoints.di.allEventGroupsJsonFileName
import com.example.athleticsrankingpoints.domain.models.EventGroup
import com.example.athleticsrankingpoints.domain.interfaces.EventGroupsRepository
import com.example.athleticsrankingpoints.domain.models.AthleticsEvent
import com.example.athleticsrankingpoints.domain.models.AthleticsEventCategory
import com.example.athleticsrankingpoints.domain.models.AthleticsSex
import kotlinx.coroutines.runBlocking

class EventGroupsRepositoryImpl(applicationContext: Context, private val rankingScoreDatabaseDao: RankingScoreDatabaseDao) : EventGroupsRepository {

  private val _isLoading = MutableLiveData(true)

  private val jsonFileName: String = allEventGroupsJsonFileName

  private var cacheListOfMaleGroups:List<EventGroup> = mutableListOf()
  private var cacheListOfFemaleGroups:List<EventGroup> = mutableListOf()

  init {
    Log.d("CACHE", "init event groups repo")

    runBlocking {
      if (!roomDatabaseExists()) {
        Log.d("CACHE", "creating room")

        val jsonFile: String = applicationContext.assets.open(jsonFileName).bufferedReader().use { it.readText() }
        val result = Klaxon().parseArray<EventGroup>(jsonFile)
        result?.let {
          insertAllEventGroups(result)
        }
      }
      _isLoading.postValue(false)
    }
    Log.d("CACHE", "finish loading event groups repo")

  }

  private suspend fun roomDatabaseExists(): Boolean {
    return rankingScoreDatabaseDao.getFirstEventGroup()!=null
  }

  override suspend fun getAllEventGroups(): List<EventGroup> {
    return rankingScoreDatabaseDao.getAllEventGroups()
  }

  override suspend fun getEventGroupsBySex(sex: AthleticsSex): List<EventGroup> {
    return getCachedCategoryList(sex).takeIf { it.isNotEmpty() }?:rankingScoreDatabaseDao.getEventGroupBySex(sex).apply {
      updateCachedCategoryList(sex, this)
    }
  }

  private fun getCachedCategoryList(sex: AthleticsSex):List<EventGroup> =
    when (sex) {
      AthleticsSex.Female -> cacheListOfFemaleGroups
      AthleticsSex.Male -> cacheListOfMaleGroups
    }

  private fun updateCachedCategoryList(sex: AthleticsSex, list: List<EventGroup>) {
    when (sex) {
      AthleticsSex.Female -> {
        Log.d("CACHE", "Caching female event groups")
        cacheListOfFemaleGroups=list
      }
      AthleticsSex.Male -> {
        Log.d("CACHE", "Caching male event groups")

        cacheListOfMaleGroups=list
      }
    }
  }

  override suspend fun getEventGroupByName(name: String): EventGroup? {
    return rankingScoreDatabaseDao.getEventGroupByName(name)
  }

  override suspend fun insertAllEventGroups(list: List<EventGroup>) {
    rankingScoreDatabaseDao.insertAllEventGroups(list)
  }

  override fun getIsRepositoryLoading(): LiveData<Boolean> = _isLoading
}