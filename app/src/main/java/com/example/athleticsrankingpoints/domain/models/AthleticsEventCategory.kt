package com.example.athleticsrankingpoints.domain.models

import com.example.athleticsrankingpoints.domain.interfaces.SelectableIdentifiable

enum class AthleticsEventCategory(val id: String) {
  category_indoor_female("category_indoor_female"),
  category_indoor_male("category_indoor_male"),
  category_outdoor_female("category_outdoor_female"),
  category_outdoor_male("category_outdoor_male")
}