package com.example.athleticsrankingpoints.data

import androidx.lifecycle.LiveData
import com.example.athleticsrankingpoints.data.entity.RankingScorePerformanceData
import com.example.athleticsrankingpoints.domain.interfaces.RankingScorePerformanceRepository

class RankingScorePerformanceRepository (private val rankingScoreDatabaseDao: RankingScoreDatabaseDao) : RankingScorePerformanceRepository {
  override suspend fun getAllRankingScorePerformances(): LiveData<List<RankingScorePerformanceData>> {
   return rankingScoreDatabaseDao.getAll()
  }

  override suspend fun getRankingScorePerformanceByName(name: String): RankingScorePerformanceData? {
    return rankingScoreDatabaseDao.getByName(name = name)
  }

  override suspend fun getRankingScorePerformanceById(id: Int): RankingScorePerformanceData? {
    return rankingScoreDatabaseDao.getById(id = id)
  }

  override suspend fun isEntryNameFree(name: String): Boolean {
    return rankingScoreDatabaseDao.getByName(name)==null
  }

  override suspend fun saveRankingScorePerformance(rankingScorePerformanceData: RankingScorePerformanceData) {
    rankingScoreDatabaseDao.insert(rankingScorePerformanceData)
  }

  override suspend fun updateRankingScorePerformance(rankingScorePerformanceData: RankingScorePerformanceData) {
    rankingScoreDatabaseDao.update(rankingScorePerformanceData)
  }

  override suspend fun deleteRankingScorePerformance(rankingScorePerformanceData: RankingScorePerformanceData) {
    rankingScoreDatabaseDao.delete(rankingScorePerformanceData)
  }
}