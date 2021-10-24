package com.example.athleticsrankingpoints.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.athleticsrankingpoints.domain.AthleticsEvent

@Composable
fun PerformancesSimulatorList(
  modifier: Modifier,
  perfList: List<String>,
  perfPointsList : List<String>,
  windsList: List<String>,
  windPointsList : List<String>,
  placementPointsList: List<String>,
  selectedEventsList: List<AthleticsEvent>,
  spinnerList: List<AthleticsEvent>,
  onEventChange: (Int, AthleticsEvent) -> Unit,
  onPerformanceChange: (Int, String) -> Unit,
  onPlacementChange: (Int, String) -> Unit,
  onWindChange: (Int, String) -> Unit,
) {
  LazyColumn(modifier = modifier) {
//    SinglePerformanceDataComponent(
//      index =-1,
//      event = AthleticsEvent.getSampleEvent(),
//      performance = "Performance e.g. 10.44",
//      performancePoints = "1059",
//      wind = "Wind, if applicable, e.g. -1.2",
//      windPoints = "7",
//      placementPoints = "Placement points",
//      spinnerList = spinnerList,
//      onEventChange = {_,_ ->},
//      onPerformanceChange = {_,_ ->},
//      onPlacementChange = {_,_ ->},
//      onWindChange = {_,_ ->},
//    )
    itemsIndexed(perfList) { index, performance ->
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



