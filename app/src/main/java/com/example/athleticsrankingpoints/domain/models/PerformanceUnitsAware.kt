package com.example.athleticsrankingpoints.domain.models

import android.util.Log
import kotlin.math.pow

class PerformanceUnitsAware(listOfUnits : List<String>, listOfUnitsNames : List<String>) {

  var performanceUnitValues = listOfUnits
  var performanceUnitsNames = listOfUnitsNames

  var performanceAbsoluteValue = "0"
    get() {
      var total = 0.0
      for (n in performanceUnitValues.indices) {
        total += (performanceUnitValues[performanceUnitValues.size-1-n].toDoubleOrNull() ?: 0.0) * (60.0.pow(n.toDouble()))
      }
      return total.toString()
    }

  fun updateUnitValues(value: String, index: Int) : List<String>{
    Log.d("MEIN CHECK", "index $index updated $value")
    return performanceUnitValues.toMutableList().also { it[index] = value }.toList()
  }

  companion object {
    fun getDefault(): PerformanceUnitsAware {
      Log.d("MEIN CHECK 3", "getting default")

      return PerformanceUnitsAware(
        AthleticsEventType.type_run.getUnitsDefaultValues(),
        AthleticsEventType.type_run.getUnits()
      )
    }
  }
}
