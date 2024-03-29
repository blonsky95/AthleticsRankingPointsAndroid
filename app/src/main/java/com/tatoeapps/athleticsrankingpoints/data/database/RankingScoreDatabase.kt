package com.tatoeapps.athleticsrankingpoints.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tatoeapps.athleticsrankingpoints.data.DataConverter
import com.tatoeapps.athleticsrankingpoints.data.entities.RankingScorePerformanceData
import com.tatoeapps.athleticsrankingpoints.domain.models.AthleticsEvent
import com.tatoeapps.athleticsrankingpoints.domain.models.CompetitionCategoryGroup
import com.tatoeapps.athleticsrankingpoints.domain.models.EventGroup

const val PERFORMANCES_TABLE_NAME = "Ranking_score_performances"
const val ATHLETICS_EVENTS_TABLE_NAME = "Athletics_events"
const val EVENT_GROUPS_TABLE_NAME = "Events_groups"
const val COMPETITION_CATEGORY_TABLE_NAME = "Competition_categories"

@Database(
  version = 1,
  entities = [
    RankingScorePerformanceData::class,
    AthleticsEvent::class,
    EventGroup::class,
    CompetitionCategoryGroup::class,
  ],
//  autoMigrations = [
//    AutoMigration(from = 2, to = 3)
//  ],
  exportSchema = true
)
@TypeConverters(DataConverter::class)
abstract class RankingScoreDatabase : RoomDatabase() {

  abstract fun rankingScoreDatabaseDao(): RankingScoreDatabaseDao

}

//migration when adding a field of last updated called date
//val MIGRATION_1_2 = object : Migration(1, 2) {
//  override fun migrate(database: SupportSQLiteDatabase) {
//    database.execSQL("ALTER TABLE $PERFORMANCES_TABLE_NAME ADD COLUMN date INTEGER")
//  }
//}