package com.example.athleticsrankingpoints.repository

import com.example.athleticsrankingpoints.domain.AthleticsEvent

interface AthleticsEventsRepository {

  val jsonFileName:String

  suspend fun getAllAthleticsEvents():List<AthleticsEvent>

  fun getAthleticEventsByCategory(category: String):List<AthleticsEvent>

  suspend fun getAthleticEventByKey(key:String):AthleticsEvent

}