package com.tatoeapps.athleticsrankingpoints.domain.interfaces

import com.tatoeapps.athleticsrankingpoints.data.entities.RankingScorePerformanceData

interface RankingScorePerformanceRepository {

  suspend fun getAllRankingScorePerformances(): List<RankingScorePerformanceData>

  suspend fun getSearchedRankingScorePerformances(searchText: String): List<RankingScorePerformanceData>

  suspend fun getRankingScorePerformanceByName(name: String): RankingScorePerformanceData?

  suspend fun getRankingScorePerformanceById(id: Int): RankingScorePerformanceData?

  suspend fun isEntryNameFree(name: String): Boolean

  suspend fun saveRankingScorePerformance(rankingScorePerformanceData: RankingScorePerformanceData)

  suspend fun updateRankingScorePerformance(rankingScorePerformanceData: RankingScorePerformanceData)

  suspend fun deleteRankingScorePerformance(rankingScorePerformanceData: RankingScorePerformanceData)

}