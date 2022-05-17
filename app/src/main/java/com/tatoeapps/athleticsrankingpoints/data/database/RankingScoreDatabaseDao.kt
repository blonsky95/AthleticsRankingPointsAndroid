package com.tatoeapps.athleticsrankingpoints.data.database

import androidx.room.*
import com.tatoeapps.athleticsrankingpoints.data.entities.RankingScorePerformanceData
import com.tatoeapps.athleticsrankingpoints.domain.models.AthleticsEvent
import com.tatoeapps.athleticsrankingpoints.domain.models.AthleticsEventCategory
import com.tatoeapps.athleticsrankingpoints.domain.models.AthleticsSex
import com.tatoeapps.athleticsrankingpoints.domain.models.EventGroup

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
  ATHLETICS EVENT GROUPS
 */
  @Query("SELECT * from $EVENT_GROUPS_TABLE_NAME")
  fun getAllEventGroups(): List<EventGroup>

  @Query("SELECT * from $EVENT_GROUPS_TABLE_NAME LIMIT 1")
  suspend fun getFirstEventGroup(): EventGroup?

  @Query("SELECT * from $EVENT_GROUPS_TABLE_NAME where sName = :name")
  suspend fun getEventGroupByName(name: String) : EventGroup?

  @Query("SELECT * from $EVENT_GROUPS_TABLE_NAME where sSex = :sex")
  suspend fun getEventGroupBySex(sex: AthleticsSex) : List<EventGroup>

  @Insert
  suspend fun insertEventGroup(eventGroup: EventGroup)

  @Insert
  suspend fun insertAllEventGroups(eventGroup: List<EventGroup>)

  /*
  RANKING SCORE PERFORMANCE DATA
   */
  @Query("SELECT * from $PERFORMANCES_TABLE_NAME ORDER BY date DESC")
  suspend fun getAllPerformances(): List<RankingScorePerformanceData>

//  @Query("SELECT * from $PERFORMANCES_TABLE_NAME where name LIKE '%:text%' OR eventGroup LIKE '%:text%'")
//  fun getPerformancesContainingText(text: String): LiveData<List<RankingScorePerformanceData>>

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