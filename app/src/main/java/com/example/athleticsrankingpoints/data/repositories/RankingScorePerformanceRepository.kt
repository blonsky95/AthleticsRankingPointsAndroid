package com.example.athleticsrankingpoints.data.repositories

import androidx.lifecycle.LiveData
import com.example.athleticsrankingpoints.data.database.RankingScoreDatabaseDao
import com.example.athleticsrankingpoints.data.entities.RankingScorePerformanceData
import com.example.athleticsrankingpoints.domain.interfaces.RankingScorePerformanceRepository
import java.util.*

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
    rankingScorePerformanceData.refreshDate()
    rankingScoreDatabaseDao.insert(rankingScorePerformanceData)
  }

  override suspend fun updateRankingScorePerformance(rankingScorePerformanceData: RankingScorePerformanceData) {
    rankingScorePerformanceData.refreshDate()
    rankingScoreDatabaseDao.update(rankingScorePerformanceData)
  }

  override suspend fun deleteRankingScorePerformance(rankingScorePerformanceData: RankingScorePerformanceData) {
    rankingScoreDatabaseDao.delete(rankingScorePerformanceData)
  }
}