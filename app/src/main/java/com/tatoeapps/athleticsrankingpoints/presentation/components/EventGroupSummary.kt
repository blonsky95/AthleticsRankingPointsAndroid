package com.tatoeapps.athleticsrankingpoints.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tatoeapps.athleticsrankingpoints.R
import com.tatoeapps.athleticsrankingpoints.domain.models.EventGroup
import com.tatoeapps.athleticsrankingpoints.presentation.theme.AthleticsRankingPointsTheme
import com.tatoeapps.athleticsrankingpoints.presentation.theme.bold
import com.tatoeapps.athleticsrankingpoints.presentation.theme.white

//Stateless composable to display info about an eventGroup

@ExperimentalAnimationApi
@Composable
fun SelectedEventGroupInfo(modifier: Modifier, contentColor: Color = white, eventGroup: EventGroup) {

  var expanded by rememberSaveable {
    mutableStateOf(true)
  }

  val context = LocalContext.current

  Column (modifier = modifier){
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 4.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = eventGroup.sName,
        style = AthleticsRankingPointsTheme.typography.text3.bold,
        color = contentColor,
        modifier = Modifier
      )
      Icon(
        painter = painterResource(id = if (expanded) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down),
        modifier = Modifier
          .padding(end = 12.dp)
          .clickable {
            expanded = !expanded
          },
        tint = contentColor,
        contentDescription = ""
      )
    }

    AnimatedVisibility(visible = expanded) {
      Column {
        Row(modifier = Modifier
          .fillMaxWidth()
        ) {
            Text(
              text = stringResource(id = R.string.min_number_performances),
              style = AthleticsRankingPointsTheme.typography.text5,
              color = contentColor,
              modifier = Modifier
            )
            Text(
              text = "${eventGroup.sMinNumberPerformancesGroup}",
              style = AthleticsRankingPointsTheme.typography.text5,
              color = contentColor,
              modifier = Modifier
            )
          }


        Row(modifier = Modifier
          .fillMaxWidth()
        ){
            Text(
              text = stringResource(id = R.string.min_number_performances_main_event),
              style = AthleticsRankingPointsTheme.typography.text5,
              color = contentColor,
              modifier = Modifier
            )
            Text(
              text = "${eventGroup.sMinNumberPerformancesMainEvent}",
              style = AthleticsRankingPointsTheme.typography.text5,
              color = contentColor,
              modifier = Modifier
            )
        }

        Text(
          text = stringResource(id = R.string.events_in_group) + eventGroup.getAllEventsInGroup().joinToString { it.getDoorInclusiveName(context) },
          style = AthleticsRankingPointsTheme.typography.text5,
          color = contentColor,
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
    SelectedEventGroupInfo(modifier = Modifier, eventGroup = EventGroup.getSampleEventGroup())
  }
}