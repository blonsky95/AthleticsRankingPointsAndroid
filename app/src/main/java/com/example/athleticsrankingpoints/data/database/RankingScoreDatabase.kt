package com.example.athleticsrankingpoints.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.athleticsrankingpoints.data.DataConverter
import com.example.athleticsrankingpoints.data.entities.PERFORMANCES_TABLE_NAME
import com.example.athleticsrankingpoints.data.entities.RankingScorePerformanceData

@Database(entities = [RankingScorePerformanceData::class], version = 2)
@TypeConverters(DataConverter::class)
abstract class RankingScoreDatabase : RoomDatabase() {

  abstract fun rankingScoreDatabaseDao(): RankingScoreDatabaseDao

}

//migration when adding a field of last updated called date
val MIGRATION_1_2 = object : Migration(1, 2) {
  override fun migrate(database: SupportSQLiteDatabase) {
    database.execSQL("ALTER TABLE $PERFORMANCES_TABLE_NAME ADD COLUMN date INTEGER")
  }
}