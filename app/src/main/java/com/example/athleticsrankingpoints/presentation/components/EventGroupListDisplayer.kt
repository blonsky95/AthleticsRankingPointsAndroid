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
import com.example.athleticsrankingpoints.domain.models.EventGroup
import com.example.athleticsrankingpoints.presentation.theme.AthleticsRankingPointsTheme

@Composable
fun EventGroupListDisplayer(modifier:Modifier=Modifier, listOfEvents: List<EventGroup>, selectedEvent: EventGroup, onEventChange: (EventGroup) -> Unit) {
  LazyColumn(modifier = modifier.padding(top = 8.dp, bottom = 8.dp)
    .background(
      color = AthleticsRankingPointsTheme.colors.textWhite,
      shape = RoundedCornerShape(6.dp)
    )
  ) {
    items(listOfEvents) {
      Row(modifier = Modifier.selectable(
        selected = selectedEvent == it,
        onClick = { onEventChange(it) }
      )) {
        var textColor = Color.Black
        val backgroundColor2:Color by animateColorAsState(
          targetValue = if (selectedEvent==it) AthleticsRankingPointsTheme.colors.background else Color.Transparent,
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
          .background(backgroundColor2, shape = RoundedCornerShape(6.dp))
          .padding(start = 4.dp, bottom = 8.dp, top = 8.dp),
          text = it.sName,
          color = textColor
        )
      }
    }
  }
}