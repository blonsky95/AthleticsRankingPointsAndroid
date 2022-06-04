package com.tatoeapps.athleticsrankingpoints.presentation.components

import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.tatoeapps.athleticsrankingpoints.presentation.theme.AthleticsRankingPointsTheme
import com.tatoeapps.athleticsrankingpoints.presentation.theme.beige

@Composable
fun DropdownItem(text: String, textColor: Color = AthleticsRankingPointsTheme.colors.backgroundComponent) {
  Text(
    text = text,
    style = AthleticsRankingPointsTheme.typography.text4,
    color = textColor
  )
  Divider(
    color = beige,
  )
}