package com.example.athleticsrankingpoints.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.athleticsrankingpoints.data.entity.RankingScorePerformanceData

@Database(entities = [RankingScorePerformanceData::class], version = 1)
@TypeConverters(DataConverter::class)
abstract class RankingScoreDatabase : RoomDatabase() {

  abstract fun rankingScoreDatabaseDao():RankingScoreDatabaseDao

}