package com.example.athleticsrankingpoints.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.athleticsrankingpoints.domain.models.AthleticsEventType

@Composable
fun PerformanceInput(inputType: AthleticsEventType = AthleticsEventType.type_run, performanceString: String, onPerformanceChange: (String) -> Unit) {
  Surface(modifier = Modifier
    .fillMaxWidth()
  ) {
    TextField(
      value = performanceString,
      onValueChange = {
        onPerformanceChange(it)

      },
      keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
    )
  }
}

@Preview
@Composable
fun previewPerformanceInput() {
  Box(modifier = Modifier.background(Color.White)) {
    PerformanceInput(performanceString ="" , onPerformanceChange = {})
  }

}