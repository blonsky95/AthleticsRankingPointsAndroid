package com.example.athleticsrankingpoints.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.athleticsrankingpoints.domain.models.PerformanceUnits
import com.example.athleticsrankingpoints.domain.models.PerformanceUnitsAware
import com.example.athleticsrankingpoints.domain.models.isValid
import com.example.athleticsrankingpoints.presentation.theme.AthleticsRankingPointsTheme
import kotlin.math.ceil
import kotlin.math.floor

@Composable
fun PerformanceInput(modifier: Modifier = Modifier, modifierForInputUnit: Modifier = Modifier, performanceUnitsAware: PerformanceUnitsAware, onPerformanceChange: (PerformanceUnitsAware) -> Unit) {

  val listOfValues = performanceUnitsAware.listOfPerformanceUnitValues.toMutableList()
  val listOfUnits = performanceUnitsAware.listOfPerformanceUnits

    Row(
      modifier = modifier,
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Start) {
      for ((index,value) in listOfValues.withIndex()) {
        PerformanceInputUnit(
          modifier = modifierForInputUnit,
          unitValue = value,
          unit = performanceUnitsAware.listOfPerformanceUnits[index],
          isUnitValueValid = performanceUnitsAware.getListOfValidUnits()[index],
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
fun PerformanceInputUnit(
  modifier: Modifier=Modifier,
  unitValue:String,
  unit:PerformanceUnits,
  isUnitValueValid:Boolean,
  onValueChange:(String) -> Unit
) {

  OutlinedTextField(
    modifier = modifier,
    value = unitValue,
    textStyle = AthleticsRankingPointsTheme.typography.text1,
    label = {
      Text(
        modifier = Modifier.padding(bottom = 2.dp),
        style = AthleticsRankingPointsTheme.typography.smalltext1,
        text = unit.unitTextString.uppercase()
      )
    },
    isError = !isUnitValueValid,
    onValueChange = {
      onValueChange(it)
    },
    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
    colors = TextFieldDefaults.outlinedTextFieldColors(
      cursorColor = AthleticsRankingPointsTheme.colors.selectedGrey,
      unfocusedBorderColor = AthleticsRankingPointsTheme.colors.selectedGrey,
      focusedBorderColor = AthleticsRankingPointsTheme.colors.textBlack.copy(alpha = ContentAlpha.medium),
      errorBorderColor = AthleticsRankingPointsTheme.colors.errorRed,
      focusedLabelColor = AthleticsRankingPointsTheme.colors.textBlack.copy(alpha = ContentAlpha.high),
      unfocusedLabelColor = AthleticsRankingPointsTheme.colors.textBlack.copy(alpha = ContentAlpha.medium),
      disabledLabelColor = AthleticsRankingPointsTheme.colors.selectedGrey.copy(ContentAlpha.disabled),
      errorLabelColor = AthleticsRankingPointsTheme.colors.errorRed,
    )
  )
}

//@Preview
//@Composable
//fun previewPerformanceInput() {
//  Box(modifier = Modifier.background(Color.White)) {
//    PerformanceInput(performanceString ="33") {}
//  }
//
//}