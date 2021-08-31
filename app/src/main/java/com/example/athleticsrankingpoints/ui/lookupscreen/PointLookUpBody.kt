package com.example.athleticsrankingpoints.ui.lookupscreen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.athleticsrankingpoints.domain.AthleticsEvent
import com.example.athleticsrankingpoints.ui.components.*
import kotlin.math.floor
import kotlin.math.pow

@Composable
fun PointLookUpBody(
  lookUpViewModel: LookUpViewModel,
  sListOfEvents:List<AthleticsEvent>
) {

//  var listOfEvents by remember { mutableStateOf(sListOfEvents) }
  val listOfEvents by lookUpViewModel.getListOfEvents().observeAsState(lookUpViewModel.sampleEventList)

//  var selectedEvent by remember{mutableStateOf( listOfEvents[0])}
  val selectedEvent by lookUpViewModel.getSelectedEvent().observeAsState(lookUpViewModel.sampleFirstEvent)

  var selectedSex by remember{mutableStateOf( AthleticsEvent.sexMale)}
  var selectedDoor by remember{mutableStateOf( AthleticsEvent.doorIndoor)}

  var performanceString by remember { mutableStateOf("0.0") }
  var performancePoints by remember { mutableStateOf("0") }

  Column(Modifier.padding(16.dp)) {

    Text(
      text = selectedEvent.sName,
      style = MaterialTheme.typography.h2,
      modifier = Modifier
        .align(Start)
        .padding(bottom = 8.dp)
    )
    Text(
      text = "Write the time or distance in the following box:",
      style = MaterialTheme.typography.body1,
      modifier = Modifier
        .align(Alignment.CenterHorizontally)
        .padding(bottom = 16.dp)
    )

    //Ideally all should like this more or less - specially if they have a custom behaviour or if they need state hoisting
    PerformanceInput(performanceString) {
      performanceString = it
      performancePoints = getPoints(athleticsEvent = selectedEvent, performance = performanceString).toString()
    }

    PointsDisplay(performancePoints)

    CategorySelector(
      selectedSex,
      selectedDoor,
      onSexSelectionChange = {
        //here should actually call a view model reset function
        selectedSex = it
//        listOfEvents=AthleticsEvent.getLongerEventsList()
        lookUpViewModel.updateEventList()
//        selectedEvent = listOfEvents[0]
        lookUpViewModel.resetSelection()
        performancePoints = "0"
        performanceString = "0.0"
        //Log.d("CAT","Current category is $selectedSex $selectedDoor and selected event is ${selectedEvent.sName}")
      },
      onDoorSelectionChange = {
        //here should actually call a view model reset function
        selectedDoor = it
//        listOfEvents=AthleticsEvent.getLongerEventsList()
        lookUpViewModel.updateEventList()
//        selectedEvent = listOfEvents[0]
        lookUpViewModel.resetSelection()
        performancePoints = "0"
        performanceString = "0.0"
      }
    )

    Divider(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
    color = Color(0xFFACACAC), thickness = 2.dp)

    EventListDisplayer(listOfEvents, selectedEvent) {
//      selectedEvent = it
      lookUpViewModel.newEventSelected(it)
    }
  }
}



fun getPoints(athleticsEvent: AthleticsEvent, performance:String) :Int  {
  return if (performance.toDoubleOrNull() == null || floor(performance.toDouble()) == 0.0) {
    0
  } else {
    pointsForRun(athleticsEvent.sCoefficients, performance.toDouble())
  }
}

fun pointsForRun(coefficients: java.util.HashMap<String, Double>, performance: Double) :Int {
  var points = 0

  points = floor(coefficients["a"]!! * (performance + coefficients["b"]!!).pow(2) + coefficients["c"]!!).toInt()

  return points
}

