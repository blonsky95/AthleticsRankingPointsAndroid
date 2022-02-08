package com.example.athleticsrankingpoints.domain.models

import android.util.Log
import kotlin.math.pow

class PerformanceUnitsAware(listOfPerformanceUnitValues : List<String>, listOfPerformanceUnits : List<PerformanceUnits>) {

  var performanceUnitValues = listOfPerformanceUnitValues
  var performanceUnits = listOfPerformanceUnits
  val performanceUnitIsValid
    get() = performanceUnits.mapIndexed {
        index, performanceUnits ->
      performanceUnits.isValid(performanceUnitValues[index])
    }

  var performanceAbsoluteValue = "0"
    get() {
      var total = 0.0
      for (n in performanceUnitValues.indices) {
        total += (performanceUnitValues[performanceUnitValues.size-1-n].toDoubleOrNull() ?: 0.0) * (60.0.pow(n.toDouble()))
      }
      return total.toString()
    }

  val isPerformanceValid = !performanceUnitIsValid.contains(false)

  companion object {
    fun getDefault(): PerformanceUnitsAware {
      return PerformanceUnitsAware(
        AthleticsEventType.type_run.getUnits().map { unit -> unit.unitDefaultValue },
        AthleticsEventType.type_run.getUnits()
      )
    }
  }
}
