package com.tatoeapps.athleticsrankingpoints

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tatoeapps.athleticsrankingpoints.data.database.RankingScoreDatabaseDao
import com.tatoeapps.athleticsrankingpoints.data.entities.RankingScorePerformanceData
import com.tatoeapps.athleticsrankingpoints.di.roomTestModule
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.GlobalContext.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertEquals


@RunWith(AndroidJUnit4::class)
class DatabaseOperations : KoinTest {

//  val db by inject<RankingScoreDatabase>()
  val rankingScoreDatabaseDao by inject<RankingScoreDatabaseDao> ()

  @Before()
  fun before() {
    loadKoinModules(roomTestModule)
  }

//  @After
//  @Throws(IOException::class)
//  fun deleteDb() {
//    db.close()
//  }

  @Test
  @Throws(Exception::class)
  fun insertAndGetRankingScore() = runBlocking {
    val rankingScore = RankingScorePerformanceData.getSampleData()
    rankingScoreDatabaseDao.insertPerformance(rankingScore)

    val oneItem = rankingScoreDatabaseDao.getPerformanceByName(rankingScore.name)
    assertEquals(oneItem?.name, rankingScore.name)
  }

  @Test
  @Throws(Exception::class)
  fun updateRankingScore() = runBlocking {

    val newRankingScore = "3000"
    val rankingScore = RankingScorePerformanceData.getSampleData()
    rankingScoreDatabaseDao.insertPerformance(rankingScore)
    val entryName = rankingScore.name
    val toBeUpdatedEntry = rankingScoreDatabaseDao.getPerformanceByName(entryName)

    toBeUpdatedEntry!!.rankingScore=newRankingScore

    rankingScoreDatabaseDao.updatePerformance(toBeUpdatedEntry)
    val updatedEntry = rankingScoreDatabaseDao.getPerformanceByName(entryName)
    assertEquals(newRankingScore, updatedEntry?.rankingScore)
  }

}