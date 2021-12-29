package com.example.athleticsrankingpoints.domain.interfaces

import androidx.lifecycle.LiveData
import arrow.core.Either
import com.example.athleticsrankingpoints.data.entity.RankingScorePerformanceData
import com.example.athleticsrankingpoints.domain.models.AthleticsEvent

interface RankingScorePerformanceRepository {

  suspend fun getAllRankingScorePerformances(): LiveData<List<RankingScorePerformanceData>>

  suspend fun getRankingScorePerformanceByName(name: String): RankingScorePerformanceData?

  suspend fun getRankingScorePerformanceById(id: Int): RankingScorePerformanceData?

  suspend fun isEntryNameFree(name: String): Boolean

  suspend fun saveRankingScorePerformance(rankingScorePerformanceData: RankingScorePerformanceData)

  suspend fun updateRankingScorePerformance(rankingScorePerformanceData: RankingScorePerformanceData)

  suspend fun deleteRankingScorePerformance(rankingScorePerformanceData: RankingScorePerformanceData)

}