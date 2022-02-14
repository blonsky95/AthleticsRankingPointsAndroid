package com.example.athleticsrankingpoints.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.athleticsrankingpoints.domain.models.EventGroup
import com.example.athleticsrankingpoints.presentation.theme.AthleticsRankingPointsTheme
import kotlin.math.exp

//Stateless composable to display info about an eventGroup

@ExperimentalAnimationApi
@Composable
fun EventGroupSummary(modifier: Modifier, eventGroup: EventGroup) {

  var expanded by rememberSaveable {
    mutableStateOf(false)
  }

  val icon = if (expanded)
    Icons.Filled.ArrowDropUp
  else
    Icons.Filled.ArrowDropDown

  Column (modifier = modifier){
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 8.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = eventGroup.sName,
        style = MaterialTheme.typography.body1,
        modifier = Modifier
      )
      Icon(
        modifier = Modifier
          .padding(end = 12.dp)
          .clickable {
            expanded = !expanded
          },
        imageVector = icon, contentDescription = "")
    }

    AnimatedVisibility(visible = expanded) {
      Column {
        Row{
          Box(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)) {
            Text(
              text = "Minimum number of performances:",
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
              text = "Minimum number of performances for main event:",
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
  }
}

@ExperimentalAnimationApi
@Composable
@Preview
fun PreviewEventGroupSummary() {
  AthleticsRankingPointsTheme() {
    EventGroupSummary(modifier = Modifier, eventGroup = EventGroup.getSampleEventGroup())
  }
}