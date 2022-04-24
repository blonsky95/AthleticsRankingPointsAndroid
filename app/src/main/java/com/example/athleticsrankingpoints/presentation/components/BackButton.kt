package com.example.athleticsrankingpoints.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.athleticsrankingpoints.presentation.theme.AthleticsRankingPointsTheme


@Composable
fun BackButton(upPress: () -> Unit) {
  IconButton(
    onClick = upPress,
    modifier = Modifier
      .padding(start = 10.dp, end = 16.dp, top = 8.dp, bottom = 4.dp)
      .size(36.dp)
//      .background(
//        color = AthleticsRankingPointsTheme.colors.backgroundComponent.copy(alpha = 0.5f),
//        shape = CircleShape
//      )
  ) {
    Icon(
      imageVector = Icons.Outlined.ArrowBack,
      tint = AthleticsRankingPointsTheme.colors.backgroundScreen,
      contentDescription = "label_back"
    )
  }
}