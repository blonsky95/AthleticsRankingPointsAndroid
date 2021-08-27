package com.example.athleticsrankingpoints.ui.lookupscreen

import android.graphics.Paint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlin.math.floor
import kotlin.math.pow

@Composable
fun PointLookUpBody() {

  val event100m = AthleticsEvent(name="100m", type = AthleticsEvent.type_run,
    coefficients = hashMapOf("a" to 24.6422116633, "b" to -16.9975315583, "c" to -0.2186620480))

  Column(Modifier.padding(16.dp)) {
    var performanceString by remember {
      mutableStateOf("0.0")
    }
    var performancePoints by remember {
      mutableStateOf("0")
    }

    Surface(modifier = Modifier.width(320.dp).padding(16.dp)) {
      TextField(value = performanceString, onValueChange = {
        performanceString = it
        performancePoints = getPoints(athleticsEvent = event100m, performance = performanceString).toString()
      },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
      )
    }

    Text(modifier = Modifier.align(CenterHorizontally).padding(16.dp),
      text = performancePoints)


  }

}

class AthleticsEvent (
  val name:String,
  val type:String, //run, jump or throw
  val coefficients:HashMap<String, Double>
    ){

  companion object{
    const val type_run="type_run" //only needs seconds and hundredths
    const val type_long_run="type_long_run" //needs minutes, seconds and hundredths
    const val type_very_long_run="type_very_long_run" //needs hours, minutes, seconds and hundredths
    const val type_jump="type_jump" //only needs metres
    const val type_throw="type_throw" //only needs metres
  }

}

fun getPoints(athleticsEvent: AthleticsEvent, performance:String) :Int  {
  return if (performance.toDoubleOrNull() == null || floor(performance.toDouble()) == 0.0) {
    0
  } else {
    pointsForRun(athleticsEvent.coefficients, performance.toDouble())
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

