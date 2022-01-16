package com.example.athleticsrankingpoints.domain.models

import com.beust.klaxon.Converter
import com.beust.klaxon.Json
import com.beust.klaxon.JsonValue
import com.example.athleticsrankingpoints.domain.interfaces.SelectableIdentifiable
import com.example.athleticsrankingpoints.domain.interfaces.findById

enum class AthleticsEventSex(override val id: String) : SelectableIdentifiable {
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

//  Converter
//  override fun canConvert(cls: Class<*>): Boolean {
//    return true
//  }
//
//  override fun fromJson(jv: JsonValue): AthleticsEventSex {
//    return Female.takeIf { jv.inside=="female" } ?: Male
//  }
//
//  override fun toJson(value: Any): String {
//    return "female".takeIf { value==Female }?:"male"
//  }
//},

