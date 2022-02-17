package com.example.athleticsrankingpoints.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.athleticsrankingpoints.presentation.theme.AthleticsRankingPointsTheme

@Composable
fun CustomDivider() {
  Divider(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
    color = AthleticsRankingPointsTheme.colors.backgroundPrimary, thickness = 2.dp)
}