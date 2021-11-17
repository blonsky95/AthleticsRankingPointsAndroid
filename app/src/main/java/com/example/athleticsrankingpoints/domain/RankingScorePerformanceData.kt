package com.example.athleticsrankingpoints.domain

import androidx.room.Entity
import kotlin.math.floor

const val PERFORMANCES_TABLE_NAME = "Ranking_score_performances"

@Entity(tableName = PERFORMANCES_TABLE_NAME)
data class RankingScorePerformanceData(
  val sName: String = "Unnamed",
//  val sIndex:Int,
//  var sEvent:AthleticsEvent,
//  var sPerformance: String,
//  var sWind: String,
//  var sPlacementPoints:String,
) {

}




