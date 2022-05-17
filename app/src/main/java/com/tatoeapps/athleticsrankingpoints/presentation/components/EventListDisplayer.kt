package com.tatoeapps.athleticsrankingpoints.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tatoeapps.athleticsrankingpoints.domain.models.AthleticsEvent
import com.tatoeapps.athleticsrankingpoints.presentation.theme.AthleticsRankingPointsTheme

@Composable
fun EventListDisplayer(modifier:Modifier=Modifier, listOfEvents: List<AthleticsEvent>, selectedEvent: AthleticsEvent, onEventChange: (AthleticsEvent) -> Unit) {
  LazyColumn(modifier = modifier
    .background(
      color = AthleticsRankingPointsTheme.colors.backgroundComponent,
      shape = RoundedCornerShape(2.dp))
    .padding(top = 8.dp, bottom = 8.dp)
  ) {
    items(listOfEvents) {
      Row(
        modifier = Modifier
          .selectable(
            selected = selectedEvent == it,
            onClick = { onEventChange(it) }
          )
          .padding(horizontal = 16.dp)) {
        val rowItemColor = if (selectedEvent==it) AthleticsRankingPointsTheme.colors.selectedComponent else Color.Transparent
        Text(modifier = Modifier
          .fillMaxWidth()
          .background(rowItemColor)
          .padding(start = 4.dp, bottom = 4.dp, top = 4.dp),
          text = it.sName,
          style = AthleticsRankingPointsTheme.typography.text3,
          color = AthleticsRankingPointsTheme.colors.textBlack
        )
      }
      Divider(modifier = Modifier.padding(horizontal = 16.dp), color = AthleticsRankingPointsTheme.colors.textBlack.copy(alpha = 0.8f), thickness = 1.dp)
    }
  }
}