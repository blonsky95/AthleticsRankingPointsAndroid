package com.tatoeapps.athleticsrankingpoints.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.tatoeapps.athleticsrankingpoints.R
import com.tatoeapps.athleticsrankingpoints.presentation.theme.AthleticsRankingPointsTheme
import com.tatoeapps.athleticsrankingpoints.presentation.theme.beige

@Composable
fun PointsDisplay(performancePoints: String) {
  Row {
      Text(
        style = AthleticsRankingPointsTheme.typography.text2.beige,
        text = stringResource(id = R.string.home_points).uppercase()
      )

      Text(
        style = AthleticsRankingPointsTheme.typography.text2.beige,
        text = performancePoints
      )
  }
}