package com.example.athleticsrankingpoints.presentation.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.athleticsrankingpoints.domain.models.AthleticsEvent
import com.example.athleticsrankingpoints.domain.models.PerformanceUnitsAware

@Composable
fun PerformancesSimulatorList(
  modifier: Modifier,
  perfList: List<PerformanceUnitsAware>,
  perfPointsList : List<String>,
  windsList: List<String>,
  windPointsList : List<String>,
  placementPointsList: List<String>,
  selectedEventsList: List<AthleticsEvent>,
  spinnerList: List<AthleticsEvent>,
  onEventChange: (Int, AthleticsEvent) -> Unit,
  onPerformanceChange: (Int, PerformanceUnitsAware) -> Unit,
  onPlacementChange: (Int, String) -> Unit,
  onWindChange: (Int, String) -> Unit,
) {
  LazyColumn(modifier = modifier) {
    itemsIndexed(selectedEventsList) { index, _ ->
      SinglePerformanceDataComponent(
        index =index,
        event =selectedEventsList[index],
        performance = perfList[index],
        performancePoints = perfPointsList[index],
        wind = windsList[index],
        windPoints = windPointsList[index],
        placementPoints = placementPointsList[index],
        spinnerList = spinnerList,
        onEventChange = onEventChange,
        onPerformanceChange = onPerformanceChange,
        onPlacementChange = onPlacementChange,
        onWindChange = onWindChange,
      )
      CustomDivider()
    }
  }
}



