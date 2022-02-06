package com.example.athleticsrankingpoints.domain.models

enum class PerformanceUnits(val unitTextString: String, val unitDefaultValue:String) {
  UNIT_SECONDS("seconds","0.0"),
  UNIT_MINUTES("minutes","0"),
  UNIT_HOURS("hours","0"),
  UNIT_METERS("metres","0.0"),
  UNIT_POINTS("points","0");

  companion object {
    private val listOfDecimalUnits = listOf(UNIT_SECONDS,UNIT_METERS)
    fun unitCanNotContainDecimal(units: PerformanceUnits)=!listOfDecimalUnits.contains(units)
  }
}

