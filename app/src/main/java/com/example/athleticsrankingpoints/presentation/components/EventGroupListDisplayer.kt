package com.example.athleticsrankingpoints.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.athleticsrankingpoints.domain.models.EventGroup

@Composable
fun EventGroupListDisplayer(modifier:Modifier, listOfEvents: List<EventGroup>, selectedEvent: EventGroup, onEventChange: (EventGroup) -> Unit) {
  LazyColumn(modifier = modifier) {
    items(listOfEvents) {
      Row(modifier = Modifier.selectable(
        selected = selectedEvent == it,
        onClick = { onEventChange(it) }
      )) {
        var backgroundColor = Color.Transparent

        if (selectedEvent == it) {
          backgroundColor = Color(0xFFACACAC)
        }
        Text(modifier = Modifier
          .fillMaxWidth()
          .background(backgroundColor)
          .padding(bottom = 8.dp, top = 8.dp),
          text = it.sName)
      }
    }
  }
}