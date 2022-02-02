package com.example.athleticsrankingpoints.data.entities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.athleticsrankingpoints.data.cache.Cacheable
import com.example.athleticsrankingpoints.data.database.PERFORMANCES_TABLE_NAME
import com.example.athleticsrankingpoints.domain.models.AthleticsEvent
import com.example.athleticsrankingpoints.domain.models.EventGroup
import java.util.*
import kotlin.math.floor

@Entity(tableName = PERFORMANCES_TABLE_NAME)
data class RankingScorePerformanceData(

  @PrimaryKey(autoGenerate = true)
  var scoreId: Int = 0,

  @NonNull
  var date: Date? = null, //date of last update

  var name: String = "Unnamed",
  val eventGroup:EventGroup,
  val performances: List<String>,
  val performancesPoints: List<String>,
  val winds: List<String>,
  val windsPoints: List<String>,
  val placementPoints: List<String>,
  val selectedEvents: List<AthleticsEvent>,
  var rankingScore: String
) :Cacheable {

  override val key: String
    get() = name

  //call to update the last updated date
  fun refreshDate() {
    this.date=Calendar.getInstance().time
  }

  companion object {

    val NEW_ENTRY = "NEW_ENTRY"

    fun getSampleData():RankingScorePerformanceData {
      return RankingScorePerformanceData(
        name = "Test name:${(floor(Math.random()*100)).toInt()} ",
        eventGroup = EventGroup.getSampleEventGroup(),
        performances = listOf("11.11","11.22","11.15","11.16","10.98",),
        performancesPoints = listOf("1111","1122","1115","1116","1098",),
        winds = listOf("0.0","0.0","0.0","0.0","0.0",),
        windsPoints = listOf("0","0","0","0","0",),
        placementPoints = listOf("0","0","0","0","0",),
        selectedEvents = listOf(AthleticsEvent.getSampleEvent(),AthleticsEvent.getSampleEvent(),AthleticsEvent.getSampleEvent(),AthleticsEvent.getSampleEvent(),AthleticsEvent.getSampleEvent(),),
        rankingScore = "1234"
      )
    }

    fun getSampleDataList(size : Int = 10):List<RankingScorePerformanceData> {
      val list = mutableListOf<RankingScorePerformanceData>().apply {
        for (i in 0 until size) {
          this.add(getSampleData())
        }
      }
      return list.toList()
    }
  }
}



