package com.example.athleticsrankingpoints.data.repositories

import androidx.lifecycle.LiveData
import com.example.athleticsrankingpoints.data.database.RankingScoreDatabaseDao
import com.example.athleticsrankingpoints.data.entities.RankingScorePerformanceData
import com.example.athleticsrankingpoints.domain.interfaces.RankingScorePerformanceRepository

class RankingScorePerformanceRepositoryImpl (private val rankingScoreDatabaseDao: RankingScoreDatabaseDao) : RankingScorePerformanceRepository {
  override suspend fun getAllRankingScorePerformances(): LiveData<List<RankingScorePerformanceData>> {
   return rankingScoreDatabaseDao.getAllPerformances()
  }

  override suspend fun getRankingScorePerformanceByName(name: String): RankingScorePerformanceData? {
    return rankingScoreDatabaseDao.getPerformanceByName(name = name)
  }

  override suspend fun getRankingScorePerformanceById(id: Int): RankingScorePerformanceData? {
    return rankingScoreDatabaseDao.getPerformanceById(id = id)
  }

  override suspend fun isEntryNameFree(name: String): Boolean {
    return rankingScoreDatabaseDao.getPerformanceByName(name)==null
  }

  override suspend fun saveRankingScorePerformance(rankingScorePerformanceData: RankingScorePerformanceData) {
    rankingScorePerformanceData.refreshDate()
    rankingScoreDatabaseDao.insertPerformance(rankingScorePerformanceData)
  }

  override suspend fun updateRankingScorePerformance(rankingScorePerformanceData: RankingScorePerformanceData) {
    rankingScorePerformanceData.refreshDate()
    rankingScoreDatabaseDao.updatePerformance(rankingScorePerformanceData)
  }

  override suspend fun deleteRankingScorePerformance(rankingScorePerformanceData: RankingScorePerformanceData) {
    rankingScoreDatabaseDao.deletePerformance(rankingScorePerformanceData)
  }
}