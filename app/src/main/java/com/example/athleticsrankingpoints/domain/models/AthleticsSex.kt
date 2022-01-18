package com.example.athleticsrankingpoints.domain.models

import com.beust.klaxon.Json
import com.example.athleticsrankingpoints.domain.interfaces.SelectableIdentifiable

enum class AthleticsSex(override val id: String) : SelectableIdentifiable {
  @Json(name = "female")
  Female("female") {
    override fun getReadableText(): String {
      return "Female"
    }
  },
  @Json(name = "male")
  Male("male") {
    override fun getReadableText(): String {
      return "Male"
    }
  }
}


