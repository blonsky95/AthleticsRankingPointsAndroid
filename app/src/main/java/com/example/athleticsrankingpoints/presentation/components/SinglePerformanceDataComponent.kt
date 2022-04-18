package com.example.athleticsrankingpoints.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.athleticsrankingpoints.R
import com.example.athleticsrankingpoints.domain.models.AthleticsEvent
import com.example.athleticsrankingpoints.domain.models.PerformanceUnitsAware
import com.example.athleticsrankingpoints.makeZeroIfEmpty
import com.example.athleticsrankingpoints.presentation.theme.AthleticsRankingPointsTheme
import com.example.athleticsrankingpoints.presentation.theme.navyBlue
import com.example.athleticsrankingpoints.presentation.theme.white
import com.example.athleticsrankingpoints.toIntOrZero

@Composable
fun SinglePerformanceDataComponent(
  index: Int,
  event: AthleticsEvent,
  performanceUnitsAware: PerformanceUnitsAware,
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

  val dropdownDrawable  = if (expanded)
    R.drawable.ic_arrow_up
  else
    R.drawable.ic_arrow_down

  Column(modifier = Modifier
    .fillMaxWidth()
    .background(Color.Transparent)
    .border(1.dp, color = AthleticsRankingPointsTheme.colors.backgroundScreen)
  ) {
    TitleAndEventAndPoints(
      modifier = Modifier.background(color = AthleticsRankingPointsTheme.colors.backgroundScreen).padding(8.dp),
      expanded, spinnerList, onEventChange, event, index, dropdownDrawable, performancePoints, windPoints, placementPoints,
      onSpinnerClick = {
        expanded=!expanded
      },
      onSpinnerDismiss = {
        expanded=false
      }
    )
    Spacer(Modifier.height(2.dp))
    PerformanceDetails(
      Modifier.padding(8.dp),
      performanceUnitsAware, performancePoints, onPerformanceChange, index, event, wind, windPoints, onWindChange, placementPoints, onPlacementChange
    )
  }

}

@Composable
private fun PerformanceDetails(
  modifier: Modifier = Modifier,
  performanceUnitsAware: PerformanceUnitsAware,
  performancePoints: String,
  onPerformanceChange: (Int, PerformanceUnitsAware) -> Unit,
  index: Int,
  event: AthleticsEvent,
  wind: String,
  windPoints: String,
  onWindChange: (Int, String) -> Unit,
  placementPoints: String,
  onPlacementChange: (Int, String) -> Unit,
) {
  Column(modifier) {
    Text(
      text = "Performance:",
      style = AthleticsRankingPointsTheme.typography.text5.navyBlue,
    )
    Spacer(Modifier.height(2.dp))
    PerformanceWithPoints(performanceUnitsAware = performanceUnitsAware, points = performancePoints, onPerformanceChange = { onPerformanceChange(index, it) })
    Spacer(Modifier.height(4.dp))
    if (event.hasWind()) {
      Text(
        text = "Wind:",
        style = AthleticsRankingPointsTheme.typography.text5.navyBlue,
      )
      WindWithPoints(wind = wind, points = windPoints, onWindChange = { onWindChange(index, it) })
      Spacer(Modifier.height(4.dp))
    }
    Text(
      text = "Placement:",
      style = AthleticsRankingPointsTheme.typography.text5.navyBlue,
    )
    PlacementWithPoints(placementPoints = placementPoints, points = placementPoints, onPlacementsPointsChange = { onPlacementChange(index, it) })
  }
}

@Composable
private fun TitleAndEventAndPoints(
  modifier: Modifier = Modifier,
  expanded: Boolean,
  spinnerList: List<AthleticsEvent>,
  onEventChange: (Int, AthleticsEvent) -> Unit,
  event: AthleticsEvent,
  index: Int,
  @DrawableRes
  dropdownDrawable : Int = R.drawable.ic_arrow_down,
  performancePoints: String,
  windPoints: String,
  placementPoints: String,
  onSpinnerClick: (Boolean) -> Unit,
  onSpinnerDismiss: () -> Unit,
) {
  MyCustomTwoComposableRow (modifier){
    TextAndSpinner(
      expanded = expanded,
      spinnerList = spinnerList,
      onEventChange = onEventChange,
      event = event,
      index = index,
      dropdownDrawable = dropdownDrawable,
      onSpinnerClick = onSpinnerClick,
      onSpinnerDismiss = onSpinnerDismiss
    )
    Text(
      modifier = Modifier.padding(horizontal = 8.dp),
      text = (performancePoints.toIntOrZero() + windPoints.toIntOrZero() + placementPoints.toIntOrZero()).toString(),
      style = AthleticsRankingPointsTheme.typography.text2.white
    )
  }
}

@Composable
fun PerformanceWithPoints(performanceUnitsAware: PerformanceUnitsAware, points: String, onPerformanceChange: (PerformanceUnitsAware) -> Unit) {
  MyCustomTwoComposableRow {
    PerformanceInput(
      customInputColors = CustomInputColors(),
      performanceUnitsAware = performanceUnitsAware,
      onPerformanceChange = onPerformanceChange
    )
    MyCustomText(
      text = points.makeZeroIfEmpty(),
    )
  }
}

@Composable
fun WindWithPoints(wind: String, points: String, onWindChange: (String) -> Unit) {
  MyCustomTwoComposableRow {
    CustomInputField(
      customInputColors = CustomInputColors(),
      value = wind,
      setMaxWidth = true,
      hint = "0.0",
      onValueChange = onWindChange
    )
//    MyCustomTextField(performance = wind, hint = "Wind (0.0)", onPerformanceChange = onWindChange)
    MyCustomText(text = points.makeZeroIfEmpty())
  }
}

@Composable
fun PlacementWithPoints(placementPoints: String, points: String, onPlacementsPointsChange: (String) -> Unit) {
  MyCustomTwoComposableRow {
    CustomInputField(
      customInputColors = CustomInputColors(),
      value = placementPoints,
      setMaxWidth = true,
      hint = "0",
      onValueChange = onPlacementsPointsChange
    )
//    MyCustomTextField(performance = placementPoints, hint = "Placement (0)", onPerformanceChange = onPlacementsPointsChange)
    MyCustomText(text = points.makeZeroIfEmpty())
  }
}


@Composable
fun TextAndSpinner(
  expanded: Boolean,
  spinnerList: List<AthleticsEvent>,
  event: AthleticsEvent,
  index: Int,
  @DrawableRes
  dropdownDrawable : Int,
  onEventChange: (Int, AthleticsEvent) -> Unit,
  onSpinnerClick: (Boolean) -> Unit,
  onSpinnerDismiss: () -> Unit
) {
  Row(
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = "Performance #${index+1}",
      style = AthleticsRankingPointsTheme.typography.text4.white
    )
    Spacer(modifier = Modifier.width(8.dp))
    AthleticEventsDropDownList(
      modifier = Modifier
        .padding(horizontal = 8.dp, vertical = 4.dp)
        .background(Color.Transparent)
        .border(1.dp, color = AthleticsRankingPointsTheme.colors.backgroundComponent)
        .padding(horizontal = 4.dp),
      expanded = expanded,
      events = spinnerList,
      selectedEvent = event,
      dropdownDrawable = dropdownDrawable,
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
      .height(IntrinsicSize.Max)
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
      .background(AthleticsRankingPointsTheme.colors.textWhite, shape = RoundedCornerShape(4.dp))
      .padding(8.dp)
      .widthIn(1.dp, Dp.Infinity)
      .heightIn(1.dp, Dp.Infinity),
    textStyle = TextStyle(color = Color.Black, fontSize = 12.sp),
    cursorBrush = SolidColor(Color.Black),
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
    style = AthleticsRankingPointsTheme.typography.text5.navyBlue,
  )
}

@Composable
fun AthleticEventsDropDownList(
  modifier: Modifier,
  expanded: Boolean,
  events: List<AthleticsEvent>,
  selectedEvent: AthleticsEvent,
  @DrawableRes
  dropdownDrawable : Int = R.drawable.ic_arrow_down,
  onDisplayClicked: (Boolean) -> Unit,
  onSelectionChange: (AthleticsEvent) -> Unit,
  onDismissRequest: () -> Unit
) {
  Column(modifier = modifier) {
    Row (
      modifier = Modifier
        .padding(vertical = 4.dp)
        .widthIn(min = 96.dp)
        .clickable {
          onDisplayClicked(expanded)
        },
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ){ 
      Text(
        text = selectedEvent.getDoorInclusiveName(),
        color = AthleticsRankingPointsTheme.colors.textWhite,
        style = TextStyle(fontSize = 13.sp)
      ) 
      Icon(
        modifier = Modifier.size(16.dp),
        painter = painterResource(id = dropdownDrawable),
        tint = AthleticsRankingPointsTheme.colors.backgroundComponent,
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
              style = AthleticsRankingPointsTheme.typography.text4,
              color = Color.Black
            )
            Divider(
              color = Color.Black,
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
      performanceUnitsAware = PerformanceUnitsAware.getDefault(perfValue = "10.55"),
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
