package com.example.athleticsrankingpoints.domain.models

import com.beust.klaxon.Json

enum class AthleticsEventType(val id: String) {
  type_run("type_run"), //only needs seconds and hundredths
  type_run_long("type_run_long"), //needs minutes, seconds and hundredths
  type_run_very_long("type_run_very_long"), //needs hours, minutes, seconds and hundredths
  type_jump("type_jump"), //only needs metres
  type_throw("type_throw"), //only needs metres
  type_combined_events("type_combined_events"), //only needs points aka Int, no decimals
}