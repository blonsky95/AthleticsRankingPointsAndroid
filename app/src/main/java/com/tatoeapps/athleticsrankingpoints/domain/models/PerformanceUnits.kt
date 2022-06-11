package com.tatoeapps.athleticsrankingpoints.domain.models

import android.content.Context
import androidx.annotation.StringRes
import com.tatoeapps.athleticsrankingpoints.R
import com.tatoeapps.athleticsrankingpoints.getResString
import kotlin.math.ceil
import kotlin.math.floor

enum class PerformanceUnits(@StringRes val unitText: Int, @StringRes val unitTextShort: Int, val unitDefaultValue:String) {
  UNIT_SECONDS(R.string.unit_seconds,R.string.unit_seconds_short,"0.0"),
  UNIT_MINUTES(R.string.unit_minutes,R.string.unit_minutes_short,"0"),
  UNIT_HOURS(R.string.unit_hours,R.string.unit_hours_short,"0"),
  UNIT_METERS(R.string.unit_metres,R.string.unit_metres_short,"0.0"),
  UNIT_POINTS(R.string.unit_points,R.string.unit_points_short,"0");

  fun getUnitText(context: Context) = getString(this.unitText, context)
  fun getUnitTextShort(context: Context) = getString(this.unitTextShort, context)

  companion object {
    private val listOfDecimalUnits = listOf(UNIT_SECONDS,UNIT_METERS)
    fun unitHasToBeWholeNumber(units: PerformanceUnits)=!listOfDecimalUnits.contains(units)
  }
}

fun PerformanceUnits.isValid(text: String):Boolean {
  var isValid = true
  if (text.toDoubleOrNull() == null && text.isNotEmpty()) {
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

private fun getString(@StringRes resInt: Int, context: Context) = context.getResString(resInt)


