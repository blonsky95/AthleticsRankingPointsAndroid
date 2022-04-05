package com.example.athleticsrankingpoints.domain.models

enum class AthleticsEventCategory(val id: String) {
  category_indoor_female("category_indoor_female"),
  category_indoor_male("category_indoor_male"),
  category_outdoor_female("category_outdoor_female"),
  category_outdoor_male("category_outdoor_male");


  fun isIndoor():Boolean = this==category_indoor_male || this ==category_indoor_female
  fun isOutdoor():Boolean = this==category_outdoor_female || this ==category_outdoor_male

}