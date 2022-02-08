package com.example.athleticsrankingpoints.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.athleticsrankingpoints.domain.models.PerformanceUnits
import com.example.athleticsrankingpoints.domain.models.PerformanceUnitsAware
import com.example.athleticsrankingpoints.domain.models.isValid
import kotlin.math.ceil
import kotlin.math.floor

@Composable
fun PerformanceInput(performanceUnitsAware: PerformanceUnitsAware, onPerformanceChange: (PerformanceUnitsAware) -> Unit) {

  val listOfValues = performanceUnitsAware.performanceUnitValues.toMutableList()
  val listOfUnits = performanceUnitsAware.performanceUnits

  Surface(modifier = Modifier
    .fillMaxWidth()
  ) {
    Row(verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Start) {
      for ((index,value) in listOfValues.withIndex()) {
        PerformanceInputUnit(
          unitValue = value,
          unit = performanceUnitsAware.performanceUnits[index],
          isUnitValueValid = performanceUnitsAware.performanceUnitIsValid[index],
          onValueChange = {newValue ->
              listOfValues[index]=newValue
              onPerformanceChange(PerformanceUnitsAware(listOfValues,listOfUnits))
          }
        )
        Spacer(modifier = Modifier.width(4.dp))
      }
    }
  }
}

@Composable
fun PerformanceInputUnit(
  unitValue:String,
  unit:PerformanceUnits,
  isUnitValueValid:Boolean,
  onValueChange:(String) -> Unit
) {

  OutlinedTextField(
    modifier = Modifier.width(80.dp),
    value = unitValue,
    label = {
      Text(
        modifier = Modifier.padding(bottom = 2.dp),
        text = unit.unitTextString
      )
    },
    isError = !isUnitValueValid,
    onValueChange = {
      onValueChange(it)
    },
    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
  )
}

//fun hasError(text:String, units: PerformanceUnits) : Boolean {
//  if (text.toDoubleOrNull()==null) return true
//  return text.toDoubleOrNull()?.let {
//     (PerformanceUnits.unitCanNotContainDecimal(units) && ceil(it)!=floor(it) )
//  }?:true
//}

//@Preview
//@Composable
//fun previewPerformanceInput() {
//  Box(modifier = Modifier.background(Color.White)) {
//    PerformanceInput(performanceString ="33") {}
//  }
//
//}