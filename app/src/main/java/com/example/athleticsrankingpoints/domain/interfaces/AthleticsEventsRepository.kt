package com.example.athleticsrankingpoints.domain.interfaces

import com.example.athleticsrankingpoints.domain.models.AthleticsEvent
import com.example.athleticsrankingpoints.domain.models.AthleticsEventCategory

interface AthleticsEventsRepository {

  suspend fun getAllAthleticsEvents():List<AthleticsEvent>

  suspend fun getAthleticEventByKey(key:String): AthleticsEvent?

  suspend fun getAthleticEventByCategory(category: AthleticsEventCategory): List<AthleticsEvent>

  suspend fun insertAllAthleticEvents(list:List<AthleticsEvent>)

}