package com.example.athleticsrankingpoints.domain

import kotlin.math.floor


data class SinglePerformanceData(
  val sIndex:Int,
  var sEvent:AthleticsEvent,
  var sPerformance: String,
  var sWind: String,
  var sPlacementPoints:String,
) {

//  val sPerformancePoints = sEvent.getPointsString(sPerformance)
//  val sWindPoints = getWindPoints(sWind).toString()
//  val sTotalPoints = (sPerformancePoints.toInt() + sWindPoints.toInt() + sPlacementPoints.toInt()).toString()

}




