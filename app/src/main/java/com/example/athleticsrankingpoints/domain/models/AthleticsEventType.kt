package com.example.athleticsrankingpoints.domain.models

enum class AthleticsEventType(val id: String) {
  type_run("type_run"), //only needs seconds and hundredths
  type_run_long("type_run_long"), //needs minutes, seconds and hundredths
  type_run_very_long("type_run_very_long"), //needs hours, minutes, seconds and hundredths
  type_jump("type_jump"), //only needs metres
  type_throw("type_throw"), //only needs metres
  type_combined_events("type_combined_events"); //only needs points aka Int, no decimals



  private val UNIT_SECONDS_DEFAULT = "0.0"
  private val UNIT_MINUTES_DEFAULT = "0"
  private val UNIT_HOURS_DEFAULT = UNIT_MINUTES_DEFAULT
  private val UNIT_METERS_DEFAULT = UNIT_SECONDS_DEFAULT
  private val UNIT_POINTS_DEFAULT = UNIT_MINUTES_DEFAULT

  fun getUnits(): List<String> =
     when (this) {
      type_run -> listOf(UNIT_SECONDS)
      type_run_long -> listOf(UNIT_MINUTES, UNIT_SECONDS)
      type_run_very_long -> listOf(UNIT_HOURS, UNIT_MINUTES, UNIT_SECONDS)
      type_jump -> listOf(UNIT_METERS)
      type_throw -> listOf(UNIT_METERS)
      type_combined_events -> listOf(UNIT_POINTS)
    }


  fun getUnitsDefaultValues():List<String> {
    return when (this) {
      type_run -> listOf(UNIT_SECONDS_DEFAULT)
      type_run_long -> listOf(UNIT_MINUTES_DEFAULT, UNIT_SECONDS_DEFAULT)
      type_run_very_long -> listOf(UNIT_HOURS_DEFAULT, UNIT_MINUTES_DEFAULT, UNIT_SECONDS_DEFAULT)
      type_jump -> listOf(UNIT_METERS_DEFAULT)
      type_throw -> listOf(UNIT_METERS_DEFAULT)
      type_combined_events -> listOf(UNIT_POINTS_DEFAULT)
    }
  }
  companion object {
    private val UNIT_SECONDS = "seconds"
    private val UNIT_MINUTES = "minutes"
    private val UNIT_HOURS = "hours"
    private val UNIT_METERS = "metres"
    private val UNIT_POINTS = "points"

    private val listOfDecimalUnits = listOf(UNIT_SECONDS,UNIT_METERS)
    fun unitCanNotContainDecimal(units: String)=!listOfDecimalUnits.contains(units)

  }

}



