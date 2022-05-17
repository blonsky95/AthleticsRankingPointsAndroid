package com.tatoeapps.athleticsrankingpoints.domain.models

enum class AthleticsEventType(val id: String) {
  type_run("type_run"), //only needs seconds and hundredths
  type_run_long("type_run_long"), //needs minutes, seconds and hundredths
  type_run_very_long("type_run_very_long"), //needs hours, minutes, seconds and hundredths
  type_jump("type_jump"), //only needs metres
  type_throw("type_throw"), //only needs metres
  type_combined_events("type_combined_events"); //only needs points aka Int, no decimals

  fun getUnits(): List<PerformanceUnits> =
     when (this) {
      type_run -> listOf(PerformanceUnits.UNIT_SECONDS)
      type_run_long -> listOf(PerformanceUnits.UNIT_MINUTES, PerformanceUnits.UNIT_SECONDS)
      type_run_very_long -> listOf(PerformanceUnits.UNIT_HOURS, PerformanceUnits.UNIT_MINUTES, PerformanceUnits.UNIT_SECONDS)
      type_jump -> listOf(PerformanceUnits.UNIT_METERS)
      type_throw -> listOf(PerformanceUnits.UNIT_METERS)
      type_combined_events -> listOf(PerformanceUnits.UNIT_POINTS)
    }
}



