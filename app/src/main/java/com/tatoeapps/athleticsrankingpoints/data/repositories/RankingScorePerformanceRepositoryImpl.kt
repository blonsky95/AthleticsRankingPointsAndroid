package com.tatoeapps.athleticsrankingpoints.data.repositories

import com.tatoeapps.athleticsrankingpoints.data.cache.Cache
import com.tatoeapps.athleticsrankingpoints.data.database.RankingScoreDatabaseDao
import com.tatoeapps.athleticsrankingpoints.data.entities.RankingScorePerformanceData
import com.tatoeapps.athleticsrankingpoints.domain.interfaces.RankingScorePerformanceRepository

class RankingScorePerformanceRepositoryImpl (
  private val rankingScoreDatabaseDao: RankingScoreDatabaseDao,
  private val cache: Cache<RankingScorePerformanceData>
) : RankingScorePerformanceRepository {


  //TODO move these variables to Cache class so no variables in this Impl
  var needsCaching = false

  private fun needsCaching() {
    needsCaching=true
  }
  private fun finishedCaching() {
    needsCaching=false
  }

  override suspend fun getAllRankingScorePerformances(): List<RankingScorePerformanceData> {
   return cache.getAll().takeIf { cache.isLoaded && !needsCaching } ?: (rankingScoreDatabaseDao.getAllPerformances().also {
     cache.saveAll(it)
     finishedCaching()
   })
  }

  override suspend fun getSearchedRankingScorePerformances(searchText: String): List<RankingScorePerformanceData> {
    return cache.getAll()
      .filter { it.name.contains(searchText, ignoreCase = true) || it.eventGroup.sName.contains(searchText, ignoreCase = true)}
  }

  override suspend fun getRankingScorePerformanceByName(name: String): RankingScorePerformanceData? {
    return cache[name].takeIf { it!=null } ?: rankingScoreDatabaseDao.getPerformanceByName(name = name).also {
      it?.let {cache.save(it)}
    }
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
    needsCaching()
  }

  override suspend fun updateRankingScorePerformance(rankingScorePerformanceData: RankingScorePerformanceData) {
    rankingScorePerformanceData.refreshDate()
    rankingScoreDatabaseDao.updatePerformance(rankingScorePerformanceData)
    needsCaching()
  }

  override suspend fun deleteRankingScorePerformance(rankingScorePerformanceData: RankingScorePerformanceData) {
    rankingScoreDatabaseDao.deletePerformance(rankingScorePerformanceData)
    needsCaching()
  }
}