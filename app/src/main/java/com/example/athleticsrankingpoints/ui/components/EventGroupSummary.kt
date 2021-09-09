package com.example.athleticsrankingpoints.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.athleticsrankingpoints.domain.EventGroup

//Stateless composable to display info about an eventGroup

@Composable
fun EventGroupSummary(modifier: Modifier,eventGroup: EventGroup) {
  Column (modifier = modifier){
    Text(
      text = eventGroup.sName,
      style = MaterialTheme.typography.body1,
      modifier = Modifier
        .align(Alignment.Start)
        .padding(bottom = 8.dp)
    )
    Row{
      Box(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 8.dp)) {
        Text(
          text = "MNP:",
          style = MaterialTheme.typography.body1,
          modifier = Modifier
            .align(Alignment.CenterStart)
        )
        Text(
          text = "${eventGroup.sMinNumberPerformancesGroup}",
          style = MaterialTheme.typography.body1,
          modifier = Modifier
            .align(Alignment.CenterEnd)
        )
      }
    }

    Row{
      Box(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 8.dp)) {
        Text(
          text = "MNPME:",
          style = MaterialTheme.typography.body1,
          modifier = Modifier
            .align(Alignment.CenterStart)
        )
        Text(
          text = "${eventGroup.sMinNumberPerformancesMainEvent}",
          style = MaterialTheme.typography.body1,
          modifier = Modifier
            .align(Alignment.CenterEnd)
        )
      }
    }

    var listOfEventNamesInGroup = ""
    val numberEvents = eventGroup.getAllEventsInGroup().size
    eventGroup.getAllEventsInGroup().forEachIndexed{index, event ->
      listOfEventNamesInGroup+=event.getDoorInclusiveName()
      if (index<numberEvents-1) {
        listOfEventNamesInGroup+=", "
      }
    }

    Text(
      text = "Events in group: $listOfEventNamesInGroup",
      style = MaterialTheme.typography.body1,
      modifier = Modifier
        .align(Alignment.Start)
        .padding(bottom = 0.dp)
    )
  }
}