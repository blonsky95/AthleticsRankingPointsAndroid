package com.example.athleticsrankingpoints.domain.models

import kotlin.math.ceil
import kotlin.math.floor

enum class PerformanceUnits(val unitTextString: String, val unitDefaultValue:String) {
  UNIT_SECONDS("seconds","0.0"),
  UNIT_MINUTES("minutes","0"),
  UNIT_HOURS("hours","0"),
  UNIT_METERS("metres","0.0"),
  UNIT_POINTS("points","0");

  companion object {
    private val listOfDecimalUnits = listOf(UNIT_SECONDS,UNIT_METERS)
    fun unitHasToBeWholeNumber(units: PerformanceUnits)=!listOfDecimalUnits.contains(units)
  }
}

fun PerformanceUnits.isValid(text: String):Boolean {
  var isValid = true
  if (text.toDoubleOrNull() == null) {
    isValid=false
  } else {
    text.toDoubleOrNull()?.let {
      if (PerformanceUnits.unitHasToBeWholeNumber(this)) {
        isValid = (ceil(it) == floor(it))
      }
    }
  }
  return isValid
}


//fun hasError(text:String, units: PerformanceUnits) : Boolean {
//  if (text.toDoubleOrNull()==null) return true
//  return text.toDoubleOrNull()?.let {
//    (PerformanceUnits.unitCanNotContainDecimal(units) && ceil(it) != floor(it) )
//  }?:true
//}

