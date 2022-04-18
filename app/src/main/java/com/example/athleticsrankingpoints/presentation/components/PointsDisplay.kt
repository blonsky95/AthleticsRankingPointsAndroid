package com.example.athleticsrankingpoints.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.athleticsrankingpoints.presentation.theme.AthleticsRankingPointsTheme
import com.example.athleticsrankingpoints.presentation.theme.beige

@Composable
fun PointsDisplay(performancePoints: String) {
  Row {
      Text(
        style = AthleticsRankingPointsTheme.typography.text2.beige,
        text = "POINTS: "
      )

      Text(
        style = AthleticsRankingPointsTheme.typography.text2.beige,
        text = "$performancePoints pts"
      )
  }
}