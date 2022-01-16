package com.example.athleticsrankingpoints.data.repositories

import android.content.Context
import android.util.Log
import com.beust.klaxon.Klaxon
import com.example.athleticsrankingpoints.data.database.RankingScoreDatabaseDao
import com.example.athleticsrankingpoints.di.allEventsJsonFileName
import com.example.athleticsrankingpoints.domain.interfaces.AthleticsEventsRepository
import com.example.athleticsrankingpoints.domain.models.AthleticsEvent
import com.example.athleticsrankingpoints.domain.models.AthleticsEventCategory
import kotlinx.coroutines.runBlocking

//There will be another repo to save stuff in the simulatr tab
class AthleticsEventsRepository(applicationContext: Context, private val rankingScoreDatabaseDao: RankingScoreDatabaseDao) : AthleticsEventsRepository {
  /*
  //TODO CONTINUE HERE
   1. On init, load txt file to repo -DONE
  2. have a method to know if room has the data - DONE
  3. every call to get something will go through room - DONE
  4. once it goes through room it is loaded in cache - variable that is here for now like before - TODO
  5. when there is a call, if that is empty, it calls room
  6. Do something that can change the isLoading in splash view model

  */

  private val jsonFileName: String = allEventsJsonFileName

  var listOfAllEvents:List<AthleticsEvent> = listOf()
  var listOfMaleIndoorEvents:MutableList<AthleticsEvent> = mutableListOf()
  var listOfFemaleIndoorEvents:MutableList<AthleticsEvent> = mutableListOf()
  var listOfMaleOutdoorEvents:MutableList<AthleticsEvent> = mutableListOf()
  var listOfFemaleOutdoorEvents:MutableList<AthleticsEvent> = mutableListOf()

  init {

    runBlocking {
      Log.d("TESTXX","Will save all events from json?: ${!roomDatabaseExists()}")

      if (!roomDatabaseExists()) {
        val jsonFile: String = applicationContext.assets.open(jsonFileName).bufferedReader().use { it.readText() }
        val result = Klaxon().parseArray<AthleticsEvent>(jsonFile)
        result?.let {
          Log.d("TESTXX", "Saving to room all events")
//          runBlocking {
            insertAllAthleticEvents(result)
//          }
          Log.d("TESTXX", "Saved to room all events")
        }
        //loading can finish
      }
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
    //check for cache 4. 5.
    return rankingScoreDatabaseDao.getAthleticEventByCategory(category)
  }

  override suspend fun insertAllAthleticEvents(list: List<AthleticsEvent>) {
    rankingScoreDatabaseDao.insertAllAthleticsEvents(list)
  }
}