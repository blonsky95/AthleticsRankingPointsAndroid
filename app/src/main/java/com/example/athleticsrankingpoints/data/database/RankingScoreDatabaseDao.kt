package com.example.athleticsrankingpoints.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.athleticsrankingpoints.data.entities.RankingScorePerformanceData
import com.example.athleticsrankingpoints.domain.models.AthleticsEvent
import com.example.athleticsrankingpoints.domain.models.AthleticsEventCategory

@Dao
interface RankingScoreDatabaseDao {
  /*
  ATHLETICS EVENT
 */
  @Query("SELECT * from $ATHLETICS_EVENTS_TABLE_NAME")
  fun getAllAthleticsEvents(): List<AthleticsEvent>

  @Query("SELECT * from $ATHLETICS_EVENTS_TABLE_NAME LIMIT 1")
  suspend fun getFirstAthleticsEvent(): AthleticsEvent?

  @Query("SELECT * from $ATHLETICS_EVENTS_TABLE_NAME where sKey = :key")
  suspend fun getAthleticEventByKey(key: String) : AthleticsEvent?

  @Query("SELECT * from $ATHLETICS_EVENTS_TABLE_NAME where sCategory = :category")
  suspend fun getAthleticEventByCategory(category: AthleticsEventCategory) : List<AthleticsEvent>

  @Insert
  suspend fun insertAthleticsEvent(athleticsEvent: AthleticsEvent)

  @Insert
  suspend fun insertAllAthleticsEvents(athleticsEvent: List<AthleticsEvent>)

  /*
  RANKING SCORE PERFORMANCE DATA
   */
  @Query("SELECT * from $PERFORMANCES_TABLE_NAME ORDER BY date DESC")
  fun getAllPerformances(): LiveData<List<RankingScorePerformanceData>>

  @Query("SELECT * from $PERFORMANCES_TABLE_NAME where name = :name")
  suspend fun getPerformanceByName(name: String) : RankingScorePerformanceData?

  @Query("SELECT * from $PERFORMANCES_TABLE_NAME where scoreId = :id")
  fun getPerformanceById(id: Int) : RankingScorePerformanceData?

  @Insert
  suspend fun insertPerformance(entry: RankingScorePerformanceData)

  @Update
  suspend fun updatePerformance(entry: RankingScorePerformanceData)

  @Delete
  suspend fun deletePerformance(entry: RankingScorePerformanceData)
}