package com.tatoeapps.athleticsrankingpoints.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tatoeapps.athleticsrankingpoints.domain.models.AthleticsEvent
import com.tatoeapps.athleticsrankingpoints.domain.models.PerformancePlacementDetails
import com.tatoeapps.athleticsrankingpoints.domain.models.PerformanceUnitsAware

@Composable
fun PerformancesSimulatorList(
  modifier: Modifier,
  performanceUnitAwareList: List<PerformanceUnitsAware>,
  perfPointsList : List<String>,
  windsList: List<String>,
  windPointsList : List<String>,
  placementPointsList: List<PerformancePlacementDetails>,
  selectedEventsList: List<AthleticsEvent>,
  spinnerList: List<AthleticsEvent>,
  onEventChange: (Int, AthleticsEvent) -> Unit,
  onPerformanceChange: (Int, PerformanceUnitsAware) -> Unit,
  onPlacementChange: (Int, PerformancePlacementDetails) -> Unit,
  onWindChange: (Int, String) -> Unit,
) {
  LazyColumn(modifier = modifier) {
    itemsIndexed(selectedEventsList) { index, _ ->
      SinglePerformanceDataComponent(
        index =index,
        event =selectedEventsList[index],
        performanceUnitsAware = performanceUnitAwareList[index],
        performancePoints = perfPointsList[index],
        wind = windsList[index],
        windPoints = windPointsList[index],
        performancePlacementDetails = placementPointsList[index],
        spinnerList = spinnerList,
        onEventChange = onEventChange,
        onPerformanceChange = onPerformanceChange,
        onPlacementChange = onPlacementChange,
        onWindChange = onWindChange,
      )
      Spacer(modifier = Modifier.height(8.dp))
    }
  }
}



