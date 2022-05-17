package com.tatoeapps.athleticsrankingpoints

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tatoeapps.athleticsrankingpoints.data.database.RankingScoreDatabase
import com.tatoeapps.athleticsrankingpoints.data.database.RankingScoreDatabaseDao
import com.tatoeapps.athleticsrankingpoints.data.entities.RankingScorePerformanceData
import com.tatoeapps.athleticsrankingpoints.di.roomTestModule
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.GlobalContext.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject
import java.io.IOException
import kotlin.test.assertEquals


@RunWith(AndroidJUnit4::class)
class DatabaseInsert : KoinTest {

  val db by inject<RankingScoreDatabase>()
  val rankingScoreDatabaseDao by inject<RankingScoreDatabaseDao> ()

  @Before()
  fun before() {
    loadKoinModules(roomTestModule)
  }

  @After
  @Throws(IOException::class)
  fun deleteDb() {
    db.close()
  }

  @Test
  @Throws(Exception::class)
  fun insertAndGetRankingScore() = runBlocking {
    val rankingScore = RankingScorePerformanceData.getSampleData()
    rankingScoreDatabaseDao.insertPerformance(rankingScore)

    val oneItem = rankingScoreDatabaseDao.getPerformanceByName(rankingScore.name)
    assertEquals(oneItem?.name, rankingScore.name)
  }

}