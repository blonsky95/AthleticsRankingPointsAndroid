package com.example.athleticsrankingpoints.ui.lookupscreen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.athleticsrankingpoints.domain.AthleticsEvent
import com.example.athleticsrankingpoints.ui.components.*
import org.koin.androidx.compose.getViewModel



@Composable
fun PointLookUpBody(
  lookUpViewModel:LookUpViewModel
) {

  val listOfEvents by lookUpViewModel.getListOfEvents().observeAsState(listOf())

  val selectedEvent by lookUpViewModel.getSelectedEvent().observeAsState(lookUpViewModel.sampleFirstEvent)

  val selectedSex by lookUpViewModel.getSelectedSex().observeAsState(AthleticsEvent.sexMale)
  val selectedDoor by lookUpViewModel.getSelectedDoor().observeAsState(AthleticsEvent.doorIndoor)

  val performanceString by lookUpViewModel.getPerformanceString().observeAsState("0.0")
  val performancePoints by lookUpViewModel.getPerformancePoints().observeAsState("0")

  Column(Modifier.padding(16.dp)) {

    Text(
      text = selectedEvent.sName,
      style = MaterialTheme.typography.h2,
      modifier = Modifier
        .align(Start)
        .padding(bottom = 8.dp)
    )
    Text(
      text = "Write the time or distance in the following box:",
      style = MaterialTheme.typography.body1,
      modifier = Modifier
        .align(Alignment.CenterHorizontally)
        .padding(bottom = 16.dp)
    )

    //Ideally all should like this more or less - specially if they have a custom behaviour or if they need state hoisting
    PerformanceInput(performanceString) {
      lookUpViewModel.updatePerformanceString(it)
    }

    PointsDisplay(performancePoints)

    CategorySelector(
      selectedSex,
      selectedDoor,
      onSexSelectionChange = {
        lookUpViewModel.updateUIWithNewSexCategory(it)
      },
      onDoorSelectionChange = {
        lookUpViewModel.updateUIWithNewDoorCategory(it)
      }
    )

    Divider(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
    color = Color(0xFFACACAC), thickness = 2.dp)

    EventListDisplayer(listOfEvents, selectedEvent) {
      lookUpViewModel.newEventSelected(it)
    }
  }
}





