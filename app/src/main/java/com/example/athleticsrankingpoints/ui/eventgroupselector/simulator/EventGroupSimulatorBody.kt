package com.example.athleticsrankingpoints.ui.eventgroupselector.simulator

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.athleticsrankingpoints.ui.components.EventGroupSummary
import org.koin.androidx.compose.getViewModel
import androidx.compose.ui.Alignment
import com.example.athleticsrankingpoints.domain.EventGroup
import com.example.athleticsrankingpoints.ui.components.CustomDivider
import com.example.athleticsrankingpoints.ui.components.PerformanceInput
import com.example.athleticsrankingpoints.ui.components.PointsDisplay
import org.koin.core.parameter.parametersOf


@Composable
fun EventGroupSimulatorBody(eventGroupName: String) {

  val eventGroupSimulatorViewModel: EventGroupSimulatorViewModel = getViewModel(parameters = {parametersOf(eventGroupName)})//INJECTED

  val eventGroup by eventGroupSimulatorViewModel.getSelectedEventGroup().observeAsState()
//  mutableStateListOf
  val performanceArray by eventGroupSimulatorViewModel.getListOfArrayOfPerformances().observeAsState(listOf())
  val pointsArray by eventGroupSimulatorViewModel.getListOfArrayOfPoints().observeAsState(listOf())

  var rankingScore by remember{ mutableStateOf("0")}

  Column(
    modifier = Modifier
      .padding(16.dp)
      .semantics { contentDescription = "Simulator Screen" }

  ) {

    EventGroupSummary(modifier = Modifier.padding(bottom = 16.dp),eventGroup = eventGroup?: EventGroup.getSampleEventGroup())

    PerformancesGrid(
      modifier = Modifier.weight(1f),
      rankingScore = rankingScore,
      perfArray = performanceArray,
      pointsArray = pointsArray
    ) { index, perf ->
      eventGroupSimulatorViewModel.updatePerformancesArray(index = index, performance = perf)
    }

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

@Composable
fun PerformancesGrid(modifier: Modifier, rankingScore:String,  perfArray: List<String>, pointsArray: List<String>, onPerformanceChange: (Int, String) -> Unit) {
  LazyColumn(modifier = modifier) {
    Log.d("GROUP SIMULATOR", "REDRAWN")
    itemsIndexed(perfArray) { index, performance ->
        Row(modifier = Modifier.padding(bottom = 8.dp)){
          Text(
            modifier = Modifier
              .align(Alignment.CenterVertically)
              .padding(8.dp),
            text = (index+1).toString()
          )
          PerformanceInput(
            modifier = Modifier.weight(1F),
            performanceString = performance) {
            onPerformanceChange(index, it)
          }

        }
        PointsDisplay(performancePoints = pointsArray[index])
      CustomDivider()
    }
  }
}

