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
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.athleticsrankingpoints.domain.models.EventGroup
import com.example.athleticsrankingpoints.presentation.theme.AthleticsRankingPointsTheme

@Composable
fun EventGroupListDisplayer(modifier:Modifier=Modifier, listOfEvents: List<EventGroup>, selectedEvent: EventGroup, onEventChange: (EventGroup) -> Unit) {
  LazyColumn(modifier = modifier
    .background(
      color = AthleticsRankingPointsTheme.colors.backgroundScreen,
    )
  ) {
    items(listOfEvents) {
      Row(modifier = Modifier
        .selectable(
          selected = selectedEvent == it,
          onClick = { onEventChange(it) }
        )
      ) {
        var textColor = AthleticsRankingPointsTheme.colors.textWhite
        val backgroundColor2:Color by animateColorAsState(
          targetValue = if (selectedEvent==it) AthleticsRankingPointsTheme.colors.backgroundComponent else Color.Transparent,
          animationSpec =  tween(
            durationMillis = 300,
            easing = LinearOutSlowInEasing
          )
        )

        if (selectedEvent == it) {
          textColor = AthleticsRankingPointsTheme.colors.textBlack
        }
        Text(modifier = Modifier
          .fillMaxWidth()
          .background(backgroundColor2)
          .padding(start = 4.dp, bottom = 4.dp, top = 4.dp),
          text = it.sName,
          style = AthleticsRankingPointsTheme.typography.text3,
          color = textColor
        )
      }
      Divider(color = AthleticsRankingPointsTheme.colors.textWhite.copy(alpha = 0.5f), thickness = 1.dp)
    }
  }
}