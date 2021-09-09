package com.example.athleticsrankingpoints.ui.eventgroupselector.simulator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.athleticsrankingpoints.ui.components.EventGroupSummary
import com.example.athleticsrankingpoints.ui.components.PerformanceInput
import com.example.athleticsrankingpoints.ui.components.PointsDisplay
import org.koin.androidx.compose.getViewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.livedata.observeAsState


@Composable
fun EventGroupSimulatorBody(eventGroupName: String) {

  val eventGroupSimulatorViewModel: EventGroupSimulatorViewModel = getViewModel() //INJECTED
  val eventGroup = eventGroupSimulatorViewModel.getEventGroup(eventGroupName)

  val performanceArray by eventGroupSimulatorViewModel.getListOfArrayOfPerformances().observeAsState()
  val pointsArray by eventGroupSimulatorViewModel.getListOfArrayOfPoints().observeAsState()

//  var performancePoints:ArrayList<String> by remember {
//    mutableStateOf(ArrayList(eventGroup.sMinNumberPerformancesGroup))
//  }

  Column(
    modifier = Modifier
      .padding(16.dp)
      .verticalScroll(rememberScrollState())
      .semantics { contentDescription = "Simulator Screen" }

  ) {
//    Text(
//      text = eventGroup?.sName ?: "Ups, problem",
//      style = MaterialTheme.typography.h2,
//      modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 16.dp)
//    )
    EventGroupSummary(modifier = Modifier.padding(bottom = 16.dp),eventGroup = eventGroup)
    for (perfN in 0..eventGroup.sMinNumberPerformancesGroup) {
        Row {
          Text(text = perfN.toString())
          PerformanceInput(
            modifier = Modifier.weight(1F),
            performanceString = performanceArray!![perfN]) {
            pointsArray!![perfN] = (it.toDouble()*3).toString()
          }

        }
        PointsDisplay(performancePoints = pointsArray!![perfN])
    }

  }
}