package com.example.athleticsrankingpoints.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.athleticsrankingpoints.ui.theme.AthleticsRankingPointsTheme

@Composable
fun PlacementPointsDisplay(placementPoints: String, onPointsChange: (String) -> Unit) {
  Row {
    Box(modifier = Modifier
      .fillMaxWidth()) {
      Text(modifier = Modifier
        .align(Alignment.CenterStart),
        style = MaterialTheme.typography.body1,
        text = "Placement Points:")

      TextField(
        modifier = Modifier
          .align(Alignment.CenterEnd).size(width = 80.dp, height = 50.dp),
        value = placementPoints,
        onValueChange = {
          onPointsChange(it)
        },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
      )
    }
    }
}

@Preview //(showBackground = true)
@Composable
fun DefaultPreview() {
  AthleticsRankingPointsTheme {
    PlacementPointsDisplay(placementPoints = "50", onPointsChange = {})  }
}