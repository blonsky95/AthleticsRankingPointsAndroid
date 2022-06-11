package com.tatoeapps.athleticsrankingpoints.domain.models

import kotlin.math.pow

class PerformanceUnitsAware(
  var listOfPerformanceUnitValues: List<String>,
  var listOfPerformanceUnits: List<PerformanceUnits>,
) {

  fun getListOfValidUnits(): List<Boolean> = listOfPerformanceUnits.mapIndexed { index, performanceUnits ->
    performanceUnits.isValid(listOfPerformanceUnitValues[index])
  }

  fun getPerformanceAbsoluteValue(): String {
    var total = 0.0
    for (n in listOfPerformanceUnitValues.indices) {
      total += (listOfPerformanceUnitValues[listOfPerformanceUnitValues.size - 1 - n].toDoubleOrNull() ?: 0.0) * (60.0.pow(n.toDouble()))
    }
    return total.toString()
  }

  fun replaceEmpties() {
    listOfPerformanceUnitValues = listOfPerformanceUnitValues.mapIndexed { index, value -> value.ifEmpty { listOfPerformanceUnits[index].unitDefaultValue } }
  }

  fun isPerformanceValid() = !getListOfValidUnits().contains(false)

  companion object {
    fun getDefault(event: AthleticsEvent = AthleticsEvent.getSampleEvent(), perfValue: String? = null): PerformanceUnitsAware {
      return PerformanceUnitsAware(
        event.sType.getUnits().map { unit -> perfValue.takeUnless { perfValue.isNullOrEmpty() } ?: "" },
        event.sType.getUnits()
      )
    }
  }
}

fun List<PerformanceUnitsAware>.replaceEmpties(): List<PerformanceUnitsAware> = this.onEach { it.replaceEmpties() }
fun List<String>.replaceEmptiesFor(newValue: String = "0"): List<String> = this.onEach { it.ifEmpty { newValue } }


