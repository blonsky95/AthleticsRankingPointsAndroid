package com.example.athleticsrankingpoints.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.athleticsrankingpoints.presentation.theme.AthleticsRankingPointsTheme

@Composable
fun PointsDisplay(performancePoints: String) {
  Row {

      Text(modifier = Modifier,
        style = AthleticsRankingPointsTheme.typography.subtitle2,
        text = "POINTS: ")

      Text(modifier = Modifier,
        style = AthleticsRankingPointsTheme.typography.subtitle2,
        text = "$performancePoints pts")

  }
}