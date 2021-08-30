package com.example.athleticsrankingpoints.ui.lookupscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.athleticsrankingpoints.domain.AthleticsEvent
import kotlin.math.floor
import kotlin.math.pow

@Composable
fun PointLookUpBody(
  listOfEvents:List<AthleticsEvent>
) {

  var selectedEvent by remember{mutableStateOf( listOfEvents[0])}

  var selectedSex by remember{mutableStateOf( AthleticsEvent.sexMale)}
  var selectedDoor by remember{mutableStateOf( AthleticsEvent.doorIndoor)}

//  var selectedColor by remember {
//    mutableStateOf(Color.Red)
//  }

  Column(Modifier.padding(16.dp)) {
    var performanceString by remember {
      mutableStateOf("0.0")
    }
    var performancePoints by remember {
      mutableStateOf("0")
    }
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
    Surface(modifier = Modifier
      .fillMaxWidth()
      .padding(bottom = 16.dp)
    ) {
      TextField(value = performanceString, onValueChange = {
        performanceString = it
        performancePoints = getPoints(athleticsEvent = selectedEvent, performance = performanceString).toString()
      },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
      )
    }

    Row{
      Box(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 8.dp)){
        Text(modifier = Modifier
          .align(Alignment.CenterStart),
          style = MaterialTheme.typography.body1,
          text = "POINTS:")

        Text(modifier = Modifier
          .align(Alignment.CenterEnd),
          style = MaterialTheme.typography.body1,
          text = performancePoints)
      }
    }

    Row(Modifier.fillMaxWidth()){
      Row (Modifier.weight(1F)) {
        AthleticsEvent.listSexOptions.forEach{
          var backgroundColor = Color.Transparent
          Box(Modifier
            .weight(1F)
            .selectable(
              selected = selectedSex == it,
              onClick = { selectedSex = it }
            )) {
            if (selectedSex == it) {
              backgroundColor = Color.Red
            }
            Text(modifier = Modifier
              .background(backgroundColor)
              .fillMaxWidth(),
              text = it)
          }

        }
      }
      Row (Modifier.weight(1F)) {
        AthleticsEvent.listDoorOptions.forEach{
          Text(modifier = Modifier
            .weight(1F)
            .selectable(
              selected = selectedDoor == it,
              onClick = { selectedDoor = it }
            ),
            text = it)
        }
      }
    }

    Divider(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
    color = Color(0xFFACACAC), thickness = 2.dp)

    LazyColumn (modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)){
      items(listOfEvents) {
        Row(modifier = Modifier.selectable(
          selected = selectedEvent == it,
          onClick = { selectedEvent = it}
        )) {
          var backgroundColor = Color.Transparent

          if (selectedEvent == it) {
            backgroundColor = Color(0xFFACACAC)
          }
          Text(modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(bottom = 8.dp, top = 8.dp),
            text = it.sName)
        }
      }
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
// when (athleticsEvent.type) {
//   AthleticsEvent.type_run -> pointsForRun(athleticsEvent.coefficients, performance)
//   AthleticsEvent.type_jump -> pointsForJump(athleticsEvent.coefficients, performance)
//   AthleticsEvent.type_throw -> pointsForThrow(athleticsEvent.coefficients, performance)
//   else -> 0
// }


fun pointsForRun(coefficients: java.util.HashMap<String, Double>, performance: Double) :Int {

//  if (false) {
//    return 0
//  }
  var points = 0

  points = floor(coefficients["a"]!! * (performance + coefficients["b"]!!).pow(2) + coefficients["c"]!!).toInt()

  return points
}

