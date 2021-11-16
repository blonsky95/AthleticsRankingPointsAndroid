package com.example.athleticsrankingpoints.domain.interfaces

import com.example.athleticsrankingpoints.domain.AthleticsEvent

interface RankingScorePerformanceRepository {
  //TODO modify
  suspend fun getAllRankingScorePerformances():List<AthleticsEvent>

  suspend fun getRankingScorePerformanceByKey(key:String):AthleticsEvent

  suspend fun saveRankingScorePerformance()

}