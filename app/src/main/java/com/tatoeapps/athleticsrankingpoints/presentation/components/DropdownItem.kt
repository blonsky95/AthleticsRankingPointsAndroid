package com.tatoeapps.athleticsrankingpoints.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tatoeapps.athleticsrankingpoints.presentation.theme.AthleticsRankingPointsTheme
import com.tatoeapps.athleticsrankingpoints.presentation.theme.beige

@Composable
fun DropdownItem(text: String, textColor: Color = AthleticsRankingPointsTheme.colors.backgroundComponent) {
  Column {
    Text(
      text = text,
      style = AthleticsRankingPointsTheme.typography.text4,
      color = textColor
    )
    Spacer(modifier = Modifier.height(2.dp))
    Divider(
      color = beige,
    )
  }

}