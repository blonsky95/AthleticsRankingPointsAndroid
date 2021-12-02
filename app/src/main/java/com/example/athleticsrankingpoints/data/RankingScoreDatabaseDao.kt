package com.example.athleticsrankingpoints.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.athleticsrankingpoints.data.entity.PERFORMANCES_TABLE_NAME
import com.example.athleticsrankingpoints.data.entity.RankingScorePerformanceData

@Dao
interface RankingScoreDatabaseDao {

  @Query("SELECT * from $PERFORMANCES_TABLE_NAME")
  fun getAll(): LiveData<List<RankingScorePerformanceData>>

  @Query("SELECT * from $PERFORMANCES_TABLE_NAME where name = :name")
  fun getByName(name: String) : RankingScorePerformanceData?

  @Query("SELECT * from $PERFORMANCES_TABLE_NAME where scoreId = :id")
  fun getById(id: Int) : RankingScorePerformanceData?

  @Insert
  suspend fun insert(entry: RankingScorePerformanceData)

  @Update
  suspend fun update(entry: RankingScorePerformanceData)

  @Delete
  suspend fun delete(entry: RankingScorePerformanceData)
}