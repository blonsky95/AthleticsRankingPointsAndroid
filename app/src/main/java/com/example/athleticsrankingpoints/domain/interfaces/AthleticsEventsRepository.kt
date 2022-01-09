package com.example.athleticsrankingpoints.domain.interfaces

import com.example.athleticsrankingpoints.domain.models.AthleticsEvent

interface AthleticsEventsRepository {

  suspend fun getAllAthleticsEvents():List<AthleticsEvent>

  fun getAthleticEventsByCategory(category: String):List<AthleticsEvent>

  suspend fun getAthleticEventByKey(key:String): AthleticsEvent

}