package com.tatoeapps.athleticsrankingpoints.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.tatoeapps.athleticsrankingpoints.domain.models.PerformanceUnits
import com.tatoeapps.athleticsrankingpoints.domain.models.PerformanceUnitsAware
import com.tatoeapps.athleticsrankingpoints.presentation.theme.AthleticsRankingPointsTheme
import com.tatoeapps.athleticsrankingpoints.presentation.theme.black
import com.tatoeapps.athleticsrankingpoints.presentation.theme.lilac

data class CustomInputColors(
  val unitTextBackground:Color = lilac,
  val unitText:Color = black,
  val valueTextBackground:Color = lilac.copy(alpha = 0.5f),
  val valueText:Color = black,
  val borderColor: Color = Color.Transparent,
  val brushColor: Color = black
)

@Composable
fun PerformanceInput(modifier: Modifier = Modifier, customInputColors:CustomInputColors = CustomInputColors(), performanceUnitsAware: PerformanceUnitsAware, onPerformanceChange: (PerformanceUnitsAware) -> Unit) {

  val listOfValues = performanceUnitsAware.listOfPerformanceUnitValues.toMutableList()
  val listOfUnits = performanceUnitsAware.listOfPerformanceUnits

  val showShortenedUnitText = listOfValues.size>1

    Row(
      modifier = modifier,
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Start) {
      for ((index,value) in listOfValues.withIndex()) {
        ReworkedPerformanceInputUnit(
          unitValue = value,
          unit = performanceUnitsAware.listOfPerformanceUnits[index],
          isUnitValueValid = performanceUnitsAware.getListOfValidUnits()[index],
          showShortUnitText = showShortenedUnitText,
          customInputColors = customInputColors,
          onValueChange = {newValue ->
            listOfValues[index]=newValue
            onPerformanceChange(PerformanceUnitsAware(listOfValues,listOfUnits))
          }
        )
        if (index<listOfValues.size-1) {
          Spacer(modifier = Modifier.width(4.dp))
        }
      }
    }
}

@Composable
fun ReworkedPerformanceInputUnit(
  modifier: Modifier = Modifier,
  unitValue: String,
  unit: PerformanceUnits,
  isUnitValueValid: Boolean,
  showShortUnitText: Boolean = true,
  customInputColors: CustomInputColors,
  onValueChange: (String) -> Unit,
) {
  Row (
    modifier = modifier
      .height(IntrinsicSize.Max)
  ){
    CustomInputField(
      customInputColors = customInputColors,
      isUnitValueValid = isUnitValueValid,
      value = unitValue,
      canFillMaxWidth = false,
      keyboardType = KeyboardType.Number,
      hint = "0",
      onValueChange = onValueChange
    )
    PerformanceUnit(customInputColors, showShortUnitText, unit)
  }
}

@Composable
private fun PerformanceUnit(customInputColors: CustomInputColors, showShortUnitText: Boolean, unit: PerformanceUnits) {
  Box(modifier = Modifier
    .fillMaxHeight()
    .background(customInputColors.unitTextBackground)
    .padding(horizontal = 16.dp),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = if (showShortUnitText) unit.getUnitTextShort(LocalContext.current) else unit.getUnitText(LocalContext.current),
      style = AthleticsRankingPointsTheme.typography.text5,
      color = customInputColors.valueText
    )
  }
}

