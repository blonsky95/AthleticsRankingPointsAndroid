package com.example.athleticsrankingpoints.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.athleticsrankingpoints.domain.AthleticsEvent
import com.example.athleticsrankingpoints.makeZeroIfEmpty
import com.example.athleticsrankingpoints.toIntOrZero

@Composable
fun SinglePerformanceDataComponent(
  index: Int,
  event: AthleticsEvent,
  performance: String,
  performancePoints: String,
  wind: String,
  windPoints: String,
  placementPoints: String,
  spinnerList: List<AthleticsEvent>,
  onEventChange: (Int, AthleticsEvent) -> Unit,
  onPerformanceChange: (Int, String) -> Unit,
  onPlacementChange: (Int, String) -> Unit,
  onWindChange: (Int, String) -> Unit,
  ) {

  var expanded by remember { mutableStateOf(false) }

  val icon = if (expanded)
    Icons.Filled.ArrowDropUp
  else
    Icons.Filled.ArrowDropDown

  Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically) {

    Column(modifier = Modifier
      .weight(1F)
    ) {
      PerformanceWithPoints(performance = performance, points = performancePoints, onPerformanceChange = {onPerformanceChange(index, it)})
      if (event.hasWind()){
        WindWithPoints(wind = wind, points = windPoints, onWindChange = {onWindChange(index, it)})
      }
      PlacementWithPoints(placementPoints = placementPoints, points = placementPoints, onPlacementsPointsChange = {onPlacementChange(index, it)})
      TotalPoints(perfId = (index+1).toString(), points = (performancePoints.toIntOrZero()+windPoints.toIntOrZero()+placementPoints.toIntOrZero()).toString())
    }

    Divider(
      modifier = Modifier
        .width(1.dp),
      //do height

      color = Color.White
    )

    Box(modifier = Modifier
    ) {
      AthleticEventsDropDownList(
        modifier = Modifier
          .padding(start = 4.dp)
          .align(Alignment.CenterEnd)
          .background(Color.DarkGray, shape = RoundedCornerShape(4.dp))
          .padding(8.dp),
        expanded = expanded,
        events = spinnerList,
        selectedEvent = event,
        icon = icon,
        onDisplayClicked = { expanded = !expanded },
        onSelectionChange = {onEventChange(index, it)},
        onDismissRequest = {expanded = false}
      )
    }

  }
}

@Composable
fun PerformanceWithPoints(performance: String, points: String, onPerformanceChange: (String) -> Unit) {
  MyCustomTwoComposableRow {
    MyCustomTextField(performance = performance, hint = "Performance (0.0)", onPerformanceChange = onPerformanceChange)
    MyCustomText(text = points.makeZeroIfEmpty())
  }
}

@Composable
fun WindWithPoints(wind: String, points: String, onWindChange: (String) -> Unit) {
  MyCustomTwoComposableRow {
    MyCustomTextField(performance = wind, hint = "Wind (0.0)", onPerformanceChange = onWindChange)
    MyCustomText(text = points.makeZeroIfEmpty())
  }
}

@Composable
fun PlacementWithPoints(placementPoints: String, points: String, onPlacementsPointsChange: (String) -> Unit) {
  MyCustomTwoComposableRow {
    MyCustomTextField(performance = placementPoints, hint = "Placement (0)", onPerformanceChange = onPlacementsPointsChange)
    MyCustomText(text = points.makeZeroIfEmpty())
  }
}


@Composable
fun TotalPoints(perfId:String, points:String) {
  MyCustomTwoComposableRow {
    Text(
      text = "Total for #$perfId",
      color = Color.White,
      style = MaterialTheme.typography.body2
    )
    Text(
      modifier = Modifier.padding(horizontal = 8.dp),
      text = points,
      style = TextStyle(
        color = Color.White,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
      ),
      )
  }
}

/**
 * Use this to place two composables at different sides of row
 */
@Composable
fun MyCustomTwoComposableRow(modifier: Modifier = Modifier, composableContent: @Composable () -> Unit) {
  Row(
    modifier = modifier
      .padding(horizontal = 8.dp, vertical = 4.dp)
      .fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    composableContent()
  }
}

@Composable
fun MyCustomTextField(performance: String = "", hint: String, onPerformanceChange: (String) -> Unit){
  BasicTextField(
    value = performance,
    onValueChange = {
      onPerformanceChange(it)
    },
    modifier = Modifier
      .background(Color.DarkGray, shape = RoundedCornerShape(4.dp))
      .padding(8.dp)
      .widthIn(1.dp, Dp.Infinity)
      .heightIn(1.dp, Dp.Infinity),
    textStyle = TextStyle(color = Color.White, fontSize = 12.sp),
    cursorBrush = SolidColor(Color.White),
    decorationBox = { innerTextField ->
        if (performance.isEmpty()) {
          Text(
            text = hint,
            style = MaterialTheme.typography.subtitle1
          )
      }
      innerTextField()
    },
    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
  )
}

@Composable
fun MyCustomText(text:String) {
  Text(
    modifier = Modifier.padding(horizontal = 8.dp),
    text = text,
    style = MaterialTheme.typography.subtitle1,
    color = Color.White
  )
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
    Row (
      modifier = Modifier.clickable {
        onDisplayClicked(expanded)
      },
      verticalAlignment = Alignment.CenterVertically
    ){ // Anchor view
      Text(
        text = selectedEvent.sName,
        color = Color.White,
        style = TextStyle(fontSize = 13.sp)
      ) // City name label
      Icon(
        imageVector = icon,
        tint = Color.White,
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
          Column {
            Text(
              text = event.sName,
              color = Color.White
            )
            Divider(
              color = Color.White,
            )
          }

        }
      }
    }
  }
}

@Composable
@Preview
fun PreviewThis() {
//  PerformanceWithPoints(performance = "11.22", points = "928") {}
  MaterialTheme {
//    SinglePerformanceDataComponent(spinnerList = AthleticsEvent.getThreeSampleEvents(), onEventChange =
//    {index, event ->
//      //do nothing
//    }
//    )
    MyCustomTextField(hint = "Performance (0.0)", onPerformanceChange = {})
  }
}
