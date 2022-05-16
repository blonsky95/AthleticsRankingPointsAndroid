package com.example.athleticsrankingpoints.domain.models

import android.content.Context
import com.beust.klaxon.Json
import com.example.athleticsrankingpoints.R
import com.example.athleticsrankingpoints.domain.interfaces.SelectableIdentifiable
import com.example.athleticsrankingpoints.getResString

enum class AthleticsSex(override val id: String) : SelectableIdentifiable {
  @Json(name = "female")
  Female("female") {
    override fun getReadableText(context: Context?): String {
      return context?.getResString(R.string.sex_female) ?: "Female"
    }
  },
  @Json(name = "male")
  Male("male") {
    override fun getReadableText(context: Context?): String {
      return context?.getResString(R.string.sex_male) ?: "Male"
    }
  }
}


