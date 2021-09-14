package com.example.athleticsrankingpoints.ui.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.athleticsrankingpoints.domain.AthleticsEvent

@Composable
fun PerformancesSimulatorList(
  modifier: Modifier,
  perfList: List<String>,
  pointsList: List<String>,
  placementPointsList: List<String>,
  selectedEventsList: List<AthleticsEvent>,
  spinnerList: List<AthleticsEvent>,
  onEventChange: (Int, AthleticsEvent) -> Unit,
  onPerformanceChange: (Int, String) -> Unit,
  onPlacementChange: (Int, String) -> Unit,
) {
  LazyColumn(modifier = modifier) {
    Log.d("GROUP SIMULATOR", "REDRAWN")
    itemsIndexed(perfList) { index, performance ->

      var expanded by remember { mutableStateOf(false) }

      val icon = if (expanded)
        Icons.Filled.ArrowDropUp //it requires androidx.compose.material:material-icons-extended
      else
        Icons.Filled.ArrowDropDown

      Row(modifier = Modifier.padding(bottom = 8.dp)){
        Column(modifier = Modifier
          .weight(1.8F)
          .padding(end = 4.dp)) {
          Text(
            modifier = Modifier
              .align(Alignment.Start)
              .padding(8.dp),
            text = (index+1).toString()
          )
          PerformanceInput(
            modifier = Modifier,
            performanceString = performance) {
            onPerformanceChange(index, it)
          }
          PlacementPointsDisplay(placementPoints = placementPointsList[index], onPointsChange = {onPlacementChange(index,it)})
          PointsDisplay(performancePoints = pointsList[index])
        }

        Box(modifier = Modifier
          .weight(1F)
          .align(Alignment.CenterVertically)) {
          AthleticEventsDropDownList(
            modifier = Modifier.align(Alignment.CenterEnd),
            expanded = expanded,
            events = spinnerList,
            selectedEvent = selectedEventsList[index],
            icon = icon,
            onDisplayClicked = { expanded = !expanded },
            onSelectionChange = {onEventChange(index, it)},
            onDismissRequest = {expanded = false}
          )
        }
      }
      CustomDivider()
    }
  }
}



@Composable
fun AthleticEventsDropDownList(
  modifier: Modifier,
  expanded: Boolean,
  events: List<AthleticsEvent>,
  selectedEvent: AthleticsEvent,
  icon : ImageVector = Icons.Filled.ArrowDropDown,
  onDisplayClicked: (Boolean) -> Unit,
  onSelectionChange: (AthleticsEvent) -> Unit,
  onDismissRequest: () -> Unit
) {
  Column(modifier = modifier) {
    Row (Modifier.clickable {
      onDisplayClicked(expanded)
    }){ // Anchor view
      Text(text = selectedEvent.sName) // City name label
      Icon(imageVector = icon,
        contentDescription = ""
      )
    }
    DropdownMenu(
      expanded = expanded,
      onDismissRequest = { onDismissRequest() }

    ) {
      events.forEach { event ->
        DropdownMenuItem(onClick = {
          onSelectionChange(event)
          onDisplayClicked(false)
        }) {
          Text(text = event.sName)
        }
      }
    }
  }
}