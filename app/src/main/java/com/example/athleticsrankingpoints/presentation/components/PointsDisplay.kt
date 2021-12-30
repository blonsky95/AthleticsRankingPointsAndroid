package com.example.athleticsrankingpoints.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PointsDisplay(performancePoints: String) {
  Row {
    Box(modifier = Modifier
      .fillMaxWidth()
      .padding(bottom = 8.dp)) {
      Text(modifier = Modifier
        .align(Alignment.CenterStart),
        style = MaterialTheme.typography.body1,
        text = "POINTS:")

      Text(modifier = Modifier
        .align(Alignment.CenterEnd),
        style = MaterialTheme.typography.body1,
        text = performancePoints)
    }
  }
}