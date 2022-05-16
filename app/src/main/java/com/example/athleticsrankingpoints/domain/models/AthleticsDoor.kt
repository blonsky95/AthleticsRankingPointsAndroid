package com.example.athleticsrankingpoints.domain.models

import android.content.Context
import com.beust.klaxon.Json
import com.example.athleticsrankingpoints.R
import com.example.athleticsrankingpoints.domain.interfaces.SelectableIdentifiable
import com.example.athleticsrankingpoints.getResString

enum class AthleticsDoor(@Json override val id: String) : SelectableIdentifiable {
  @Json(name = "indoor")
  Indoor("indoor") {
    override fun getReadableText(context: Context?): String {
      return context?.getResString(R.string.door_indoor) ?: "Indoor"
    }
  },
  @Json(name = "outdoor")
  Outdoor("outdoor") {
    override fun getReadableText(context: Context?): String {
      return context?.getResString(R.string.door_outdoor) ?: "Outdoor"
    }
  }
}