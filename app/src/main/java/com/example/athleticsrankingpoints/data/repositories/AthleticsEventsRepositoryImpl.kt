package com.example.athleticsrankingpoints.data.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.beust.klaxon.Klaxon
import com.example.athleticsrankingpoints.data.database.RankingScoreDatabaseDao
import com.example.athleticsrankingpoints.di.allEventsJsonFileName
import com.example.athleticsrankingpoints.domain.interfaces.AthleticsEventsRepository
import com.example.athleticsrankingpoints.domain.models.AthleticsEvent
import com.example.athleticsrankingpoints.domain.models.AthleticsEventCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking

class AthleticsEventsRepositoryImpl(applicationContext: Context, private val rankingScoreDatabaseDao: RankingScoreDatabaseDao) : AthleticsEventsRepository {

  private val _isLoading = MutableLiveData(true)

  private val jsonFileName: String = allEventsJsonFileName

  private var cacheListOfMaleIndoorEvents:List<AthleticsEvent> = listOf()
  private var cacheListOfFemaleIndoorEvents:List<AthleticsEvent> = listOf()
  private var cacheListOfMaleOutdoorEvents:List<AthleticsEvent> = listOf()
  private var cacheListOfFemaleOutdoorEvents:List<AthleticsEvent> = listOf()

  init {
    Log.d("TESTXX","Repo has been init")

    runBlocking {
      if (!roomDatabaseExists()) {
        val jsonFile: String = applicationContext.assets.open(jsonFileName).bufferedReader().use { it.readText() }
        val result = Klaxon().parseArray<AthleticsEvent>(jsonFile)
        result?.let {
          insertAllAthleticEvents(result)
        }
      }
      _isLoading.postValue(false)
    }
  }



  private suspend fun roomDatabaseExists(): Boolean {
    return rankingScoreDatabaseDao.getFirstAthleticsEvent()!=null
  }

  override suspend fun getAllAthleticsEvents(): List<AthleticsEvent> {
    return rankingScoreDatabaseDao.getAllAthleticsEvents()
  }

  override suspend fun getAthleticEventByKey(key: String): AthleticsEvent? {
    return rankingScoreDatabaseDao.getAthleticEventByKey(key = key)
  }

  override suspend fun getAthleticEventByCategory(category: AthleticsEventCategory): List<AthleticsEvent> {
    return getCachedCategoryList(category).takeIf { it.isNotEmpty() }?:rankingScoreDatabaseDao.getAthleticEventByCategory(category).apply {
      updateCachedCategoryList(category, this)
    }
  }

  override suspend fun insertAllAthleticEvents(list: List<AthleticsEvent>) {
    rankingScoreDatabaseDao.insertAllAthleticsEvents(list)
  }

  override fun getIsRepositoryLoading(): LiveData<Boolean> = _isLoading

  private fun getCachedCategoryList(category: AthleticsEventCategory):List<AthleticsEvent> =
    when (category) {
      AthleticsEventCategory.category_indoor_female -> cacheListOfFemaleIndoorEvents
      AthleticsEventCategory.category_indoor_male -> cacheListOfMaleIndoorEvents
      AthleticsEventCategory.category_outdoor_female -> cacheListOfFemaleOutdoorEvents
      AthleticsEventCategory.category_outdoor_male -> cacheListOfMaleOutdoorEvents
    }

  private fun updateCachedCategoryList(category: AthleticsEventCategory, list: List<AthleticsEvent>) {
    when (category) {
      AthleticsEventCategory.category_indoor_female -> {
        cacheListOfFemaleIndoorEvents = list
      }
      AthleticsEventCategory.category_indoor_male ->  {
        cacheListOfMaleIndoorEvents = list
      }
      AthleticsEventCategory.category_outdoor_female ->{
        cacheListOfFemaleOutdoorEvents = list
      }
      AthleticsEventCategory.category_outdoor_male -> {
        cacheListOfMaleOutdoorEvents = list
      }
    }
  }
}