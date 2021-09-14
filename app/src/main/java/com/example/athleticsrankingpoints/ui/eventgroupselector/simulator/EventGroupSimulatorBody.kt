package com.example.athleticsrankingpoints.ui.eventgroupselector.simulator

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.getViewModel
import androidx.compose.ui.Alignment
import com.example.athleticsrankingpoints.domain.EventGroup
import com.example.athleticsrankingpoints.ui.components.*
import org.koin.core.parameter.parametersOf


@Composable
fun EventGroupSimulatorBody(eventGroupName: String) {

  val eventGroupSimulatorViewModel: EventGroupSimulatorViewModel = getViewModel(parameters = {parametersOf(eventGroupName)})//INJECTED

  val eventGroup by eventGroupSimulatorViewModel.getSelectedEventGroup().observeAsState()

  val performanceList by eventGroupSimulatorViewModel.getListOfPerformances().observeAsState(listOf())
  val pointsList by eventGroupSimulatorViewModel.getListOfPoints().observeAsState(listOf())
  val selectedEventsList by eventGroupSimulatorViewModel.getListOfSelectedEvents().observeAsState(listOf())
  val placementPointsList by eventGroupSimulatorViewModel.getListOfPlacementPoints().observeAsState(listOf())

  val rankingScore by eventGroupSimulatorViewModel.getRankingScore().observeAsState("0")

  Column(
    modifier = Modifier
      .padding(16.dp)
      .semantics { contentDescription = "Simulator Screen" }

  ) {

    EventGroupSummary(modifier = Modifier.padding(bottom = 16.dp),eventGroup = eventGroup?: EventGroup.getSampleEventGroup())
    PerformancesSimulatorList(
      modifier = Modifier.weight(1f),
      perfList = performanceList,
      pointsList = pointsList,
      selectedEventsList = selectedEventsList,
      placementPointsList = placementPointsList,
      spinnerList = eventGroup!!.getAllEventsInGroup(),
      onEventChange = {index, event ->
        eventGroupSimulatorViewModel.updateSelectedEvent(index = index, event = event)},
      onPerformanceChange = { index, perf ->
        eventGroupSimulatorViewModel.updatePerformancesList(index = index, performance = perf)
      },
      onPlacementChange = {index, placement ->
        eventGroupSimulatorViewModel.updatePlacementPointsList(index = index, points = placement)
      }

    )

    Row (Modifier.padding(top = 16.dp)){
      Box(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 8.dp)) {
        Text(modifier = Modifier
          .align(Alignment.CenterStart),
          style = MaterialTheme.typography.body1,
          text = "Ranking Score:")

        Text(modifier = Modifier
          .align(Alignment.CenterEnd),
          style = MaterialTheme.typography.body1,
          text = rankingScore)
      }
    }

  }
}

