package com.example.athleticsrankingpoints.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.athleticsrankingpoints.domain.models.AthleticsEvent
import com.example.athleticsrankingpoints.presentation.theme.AthleticsRankingPointsTheme

@Composable
fun EventListDisplayer(modifier:Modifier=Modifier, listOfEvents: List<AthleticsEvent>, selectedEvent: AthleticsEvent, onEventChange: (AthleticsEvent) -> Unit) {
  LazyColumn(modifier = modifier.padding(top = 8.dp, bottom = 8.dp)
    .background(
      color = AthleticsRankingPointsTheme.colors.backgroundGrey.copy(alpha = 0.6f),
      shape = RoundedCornerShape(6.dp))
  ) {
    items(listOfEvents) {
      Row(modifier = Modifier.selectable(
        selected = selectedEvent == it,
        onClick = { onEventChange(it) }
      )) {
        var textColor = Color.Black
//        val rowItemColor:Color by animateColorAsState(
//          targetValue = if (selectedEvent==it) AthleticsRankingPointsTheme.colors.selectedGrey.copy(alpha = 0.6f) else Color.Transparent
//        )

        val rowItemColor = if (selectedEvent==it) AthleticsRankingPointsTheme.colors.selectedGrey.copy(alpha = 0.6f) else Color.Transparent


        if (selectedEvent == it) {
          textColor = AthleticsRankingPointsTheme.colors.textWhite
        }
        Text(modifier = Modifier
          .fillMaxWidth()
          .background(rowItemColor)
          .padding(start = 4.dp, bottom = 4.dp, top = 4.dp),
          text = it.sName,
          style = AthleticsRankingPointsTheme.typography.text1,
          color = textColor
        )
      }
    }
  }
}