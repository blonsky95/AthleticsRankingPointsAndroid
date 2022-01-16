package com.example.athleticsrankingpoints.domain.models

import com.beust.klaxon.Json
import com.example.athleticsrankingpoints.domain.interfaces.SelectableIdentifiable

enum class AthleticsEventDoor(@Json override val id: String) : SelectableIdentifiable {
  @Json(name = "indoor")
  Indoor("indoor") {
    override fun getReadableText(): String {
      return "Indoor"
    }
  },
  @Json(name = "outdoor")
  Outdoor("outdoor") {
    override fun getReadableText(): String {
      return "Outdoor"
    }
  }
}