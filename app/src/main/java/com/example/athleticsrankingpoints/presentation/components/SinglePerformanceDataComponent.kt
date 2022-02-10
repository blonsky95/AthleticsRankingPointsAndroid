package com.example.athleticsrankingpoints.presentation.components

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
import com.example.athleticsrankingpoints.domain.models.AthleticsEvent
import com.example.athleticsrankingpoints.domain.models.PerformanceUnitsAware
import com.example.athleticsrankingpoints.makeZeroIfEmpty
import com.example.athleticsrankingpoints.toIntOrZero

@Composable
fun SinglePerformanceDataComponent(
  index: Int,
  event: AthleticsEvent,
  performance: PerformanceUnitsAware,
  performancePoints: String,
  wind: String,
  windPoints: String,
  placementPoints: String,
  spinnerList: List<AthleticsEvent>,
  onEventChange: (Int, AthleticsEvent) -> Unit,
  onPerformanceChange: (Int, PerformanceUnitsAware) -> Unit,
  onPlacementChange: (Int, String) -> Unit,
  onWindChange: (Int, String) -> Unit,
  ) {

  var expanded by remember { mutableStateOf(false) }

  val icon = if (expanded)
    Icons.Filled.ArrowDropUp
  else
    Icons.Filled.ArrowDropDown

  Column(modifier = Modifier
    .fillMaxWidth()
  ) {
    Spacer(modifier = Modifier.height(4.dp))

    TitleAndEventAndPoints(expanded, spinnerList, onEventChange, event, index, icon, performancePoints, windPoints, placementPoints,
      onSpinnerClick = {
        expanded=!expanded
      },
      onSpinnerDismiss = {
        expanded=false
      }
    )

    PerformanceWithPoints(performance = performance, points = performancePoints, onPerformanceChange = {onPerformanceChange(index, it)})
    if (event.hasWind()){
      WindWithPoints(wind = wind, points = windPoints, onWindChange = {onWindChange(index, it)})
    }
    PlacementWithPoints(placementPoints = placementPoints, points = placementPoints, onPlacementsPointsChange = {onPlacementChange(index, it)})
  }

  Divider(
      modifier = Modifier
        .width(1.dp),
      color = Color.White
    )
}

@Composable
private fun TitleAndEventAndPoints(
  expanded: Boolean,
  spinnerList: List<AthleticsEvent>,
  onEventChange: (Int, AthleticsEvent) -> Unit,
  event: AthleticsEvent,
  index: Int,
  icon: ImageVector,
  performancePoints: String,
  windPoints: String,
  placementPoints: String,
  onSpinnerClick: (Boolean) -> Unit,
  onSpinnerDismiss: () -> Unit
) {
  MyCustomTwoComposableRow {
    TextAndSpinner(
      expanded = expanded,
      spinnerList = spinnerList,
      onEventChange = onEventChange,
      event = event,
      index = index,
      icon = icon,
      onSpinnerClick = onSpinnerClick,
      onSpinnerDismiss = onSpinnerDismiss
    )
    Text(
      modifier = Modifier.padding(horizontal = 8.dp),
      text = (performancePoints.toIntOrZero() + windPoints.toIntOrZero() + placementPoints.toIntOrZero()).toString(),
      style = TextStyle(
        color = Color.White,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
      ),
    )
  }
}

@Composable
fun PerformanceWithPoints(performance: PerformanceUnitsAware, points: String, onPerformanceChange: (PerformanceUnitsAware) -> Unit) {
  MyCustomTwoComposableRow {
    PerformanceInput(
      modifier = Modifier
        .background(Color.DarkGray, shape = RoundedCornerShape(4.dp))
        .padding(4.dp),
      modifierForInputUnit = Modifier.width(80.dp),
      performanceUnitsAware = performance,
      onPerformanceChange = onPerformanceChange
    )
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
fun TextAndSpinner(
  expanded: Boolean,
  spinnerList: List<AthleticsEvent>,
  event: AthleticsEvent,
  index: Int,
  icon: ImageVector,
  onEventChange: (Int, AthleticsEvent) -> Unit,
  onSpinnerClick: (Boolean) -> Unit,
  onSpinnerDismiss: () -> Unit
) {
  Row(
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = "Performance #${index+1}",
      color = Color.White,
      style = MaterialTheme.typography.body2
    )
    Spacer(modifier = Modifier.width(4.dp))
    AthleticEventsDropDownList(
      modifier = Modifier
        .padding(horizontal = 8.dp, vertical = 4.dp)
        .background(Color.DarkGray, shape = RoundedCornerShape(4.dp))
        .padding(4.dp),
      expanded = expanded,
      events = spinnerList,
      selectedEvent = event,
      icon = icon,
      onDisplayClicked = onSpinnerClick,
      onSelectionChange = {
        onEventChange(index, it)
      },
      onDismissRequest = onSpinnerDismiss
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
    ){ 
      Text(
        text = selectedEvent.sName,
        color = Color.White,
        style = TextStyle(fontSize = 13.sp)
      ) 
      Spacer(modifier = Modifier.width(4.dp))
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
              text = event.getDoorInclusiveName(),
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
fun PreviewSinglePerformanceDataComponent() {
  MaterialTheme {
    SinglePerformanceDataComponent(

      spinnerList = AthleticsEvent.getThreeSampleEvents(),
      onEventChange =
    {index, event ->
      //do nothing
    },
      index = 0,
      event = AthleticsEvent.getSampleEvent(),
      performance = PerformanceUnitsAware.getDefault(perfValue = "10.55"),
      performancePoints = "502",
      wind = "1.2",
      windPoints = "0",
      placementPoints = "45",
      onWindChange = {index, string -> },
      onPlacementChange = {index, string -> },
      onPerformanceChange = {index, pua -> }
      )
  }
}
