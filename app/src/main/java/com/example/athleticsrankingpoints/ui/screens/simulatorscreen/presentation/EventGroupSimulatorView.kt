package com.example.athleticsrankingpoints.ui.simulatorscreen.simulator

import android.util.Log
import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.getViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.example.athleticsrankingpoints.domain.EventGroup
import com.example.athleticsrankingpoints.ui.components.*
import com.example.athleticsrankingpoints.ui.screens.simulatorscreen.EventGroupSimulatorViewModel
import com.example.athleticsrankingpoints.ui.theme.MyDarkGray
import org.koin.core.parameter.parametersOf


@Composable
fun EventGroupSimulatorBody(eventGroupName: String) {

  val viewModel: EventGroupSimulatorViewModel = getViewModel(parameters = {parametersOf(eventGroupName)})//INJECTED

  val title by viewModel.getTitle().observeAsState("")

  val eventGroup by viewModel.getSelectedEventGroup().observeAsState()

  val performanceList by viewModel.getListOfPerformances().observeAsState(listOf())
  val performancePointsList by viewModel.getListOfPerformancePoints().observeAsState(listOf())
  val windsList by viewModel.getListOfWinds().observeAsState(listOf())
  val windsPointsList by viewModel.getListOfWindsPoints().observeAsState(listOf())
  val selectedEventsList by viewModel.getListOfSelectedEvents().observeAsState(listOf())
  val placementPointsList by viewModel.getListOfPlacementPoints().observeAsState(listOf())

  Column(
    modifier = Modifier
      .padding(16.dp)
      .semantics { contentDescription = "Simulator Screen" }

  ) {
    Spacer(modifier = Modifier.height(8.dp))
    MyPerformanceTitleTextField(title = title, onValueChange = {viewModel.updateTitle(it)}, hint = "Title")
    Spacer(modifier = Modifier.height(4.dp))
    EventGroupSummary(modifier = Modifier.padding(bottom = 16.dp),eventGroup = eventGroup?: EventGroup.getSampleEventGroup())
    PerformancesSimulatorList(
      modifier = Modifier
        .weight(1f)
        .background(color = MyDarkGray, shape = RoundedCornerShape(4.dp)),
      perfList = performanceList,
      perfPointsList = performancePointsList,
      windsList = windsList,
      windPointsList = windsPointsList,
      selectedEventsList = selectedEventsList,
      placementPointsList = placementPointsList,
      spinnerList = eventGroup!!.getAllEventsInGroup(),
      onEventChange = {index, event ->
        viewModel.updateSelectedEvent(index = index, event = event)
      },
      onPerformanceChange = { index, perf ->
        viewModel.updatePerformancesList(index = index, performance = perf)
      },
      onPlacementChange = {index, placement ->
        viewModel.updatePlacementPointsList(index = index, points = placement)
      },
      onWindChange = {index, wind ->
        viewModel.updateWindList(index = index, wind = wind)
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
          text = viewModel.getRankingScore(performancePointsList,placementPointsList, windsPointsList))
      }
    }
//    Spacer(modifier = Modifier.height(8.dp))
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.End
    ) {
        CustomButton(text = "SAVE") {
          Log.d("BROH", "GO TO SAVE")
          viewModel.saveTotalPerformance()
      }

    }

  }
}

@Composable
fun MyPerformanceTitleTextField(title:String, onValueChange : (String) -> Unit, hint:String) {
  BasicTextField(
    value = title,
    onValueChange = {
      onValueChange(it)
    },
    modifier = Modifier
      .background(Color(0xFF3C3C3C), shape = RoundedCornerShape(4.dp))
      .padding(8.dp)
      .widthIn(1.dp, Dp.Infinity)
      .heightIn(1.dp, Dp.Infinity),
    textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
    cursorBrush = SolidColor(Color.White),
    decorationBox = { innerTextField ->
      if (title.isEmpty()) {
        Text(
          text = hint,
          style = TextStyle(color = Color.LightGray, fontWeight = FontWeight.Light, fontSize = 18.sp)
        )
      }
      innerTextField()
    },
  )
}



