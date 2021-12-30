package com.example.athleticsrankingpoints.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun PerformanceInput(modifier: Modifier, performanceString: String, onPerformanceChange: (String) -> Unit) {
  Surface(modifier = modifier
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