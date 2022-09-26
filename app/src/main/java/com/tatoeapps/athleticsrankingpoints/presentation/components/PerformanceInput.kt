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
import androidx.compose.ui.unit.dp
import com.tatoeapps.athleticsrankingpoints.domain.models.PerformanceUnits
import com.tatoeapps.athleticsrankingpoints.domain.models.PerformanceUnitsAware
import com.tatoeapps.athleticsrankingpoints.presentation.theme.AthleticsRankingPointsTheme
import com.tatoeapps.athleticsrankingpoints.presentation.theme.black
import com.tatoeapps.athleticsrankingpoints.presentation.theme.lilac
import com.tatoeapps.athleticsrankingpoints.presentation.theme.white

data class CustomInputColors(
  val unitTextBackground: Color = lilac,
  val unitText: Color = black,
  val valueTextBackground: Color = lilac.copy(alpha = 0.5f),
  val valueText: Color = black,
  val borderColor: Color = Color.Transparent,
  val brushColor: Color = black,
)

sealed class InputFieldColourStyle(val colors: CustomInputColors) {
  object HomeInputFieldColour : InputFieldColourStyle(
    CustomInputColors(
      unitText = white,
      valueText = white,
      valueTextBackground = Color.Transparent,
      unitTextBackground = lilac,
      borderColor = lilac.copy(alpha = 0.5f),
      brushColor = white.copy(alpha = 0.8f)
    ))

  object CalculatorPerformanceInputFieldColour : InputFieldColourStyle(CustomInputColors())
}

@Composable
fun PerformanceInput(modifier: Modifier = Modifier, colorStyle: InputFieldColourStyle, performanceUnitsAware: PerformanceUnitsAware, onPerformanceChange: (PerformanceUnitsAware) -> Unit) {

  val listOfValues = performanceUnitsAware.listOfPerformanceUnitValues.toMutableList()
  val listOfUnits = performanceUnitsAware.listOfPerformanceUnits

  val showShortenedUnitText = listOfValues.size > 1

  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Start) {
    for ((index, value) in listOfValues.withIndex()) {
      PerformanceInputUnit(
        unitValue = value,
        unit = performanceUnitsAware.listOfPerformanceUnits[index],
        isUnitValueValid = performanceUnitsAware.getListOfValidUnits()[index],
        colorStyle = colorStyle,
        showShortUnitText = showShortenedUnitText,
        onValueChange = { newValue ->
          listOfValues[index] = newValue
          onPerformanceChange(PerformanceUnitsAware(listOfValues, listOfUnits))
        }
      )
      if (index < listOfValues.size - 1) {
        Spacer(modifier = Modifier.width(4.dp))
      }
    }
  }
}

@Composable
fun PerformanceInputUnit(
  modifier: Modifier = Modifier,
  unitValue: String,
  unit: PerformanceUnits,
  isUnitValueValid: Boolean,
  colorStyle: InputFieldColourStyle,
  showShortUnitText: Boolean = true,
  onValueChange: (String) -> Unit,
) {
  Row(
    modifier = modifier
      .height(IntrinsicSize.Max)
  ) {
    PerformanceInputField(
      isUnitValueValid = isUnitValueValid,
      unitValue = unitValue,
      colorStyle = colorStyle,
      hint = unit.unitDefaultValue,
      onValueChange = onValueChange
    )
    PerformanceUnit(showShortUnitText = showShortUnitText, unit = unit)
  }
}

@Composable
private fun PerformanceUnit(customInputColors: CustomInputColors = CustomInputColors(), showShortUnitText: Boolean, unit: PerformanceUnits) {
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


