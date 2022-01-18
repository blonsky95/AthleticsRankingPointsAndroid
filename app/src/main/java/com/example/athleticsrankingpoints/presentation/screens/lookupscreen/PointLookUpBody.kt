package com.example.athleticsrankingpoints.presentation.lookupscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.athleticsrankingpoints.domain.interfaces.findById
import com.example.athleticsrankingpoints.domain.models.AthleticsDoor
import com.example.athleticsrankingpoints.domain.models.AthleticsSex
import com.example.athleticsrankingpoints.presentation.components.*
import com.example.athleticsrankingpoints.presentation.screens.lookupscreen.LookUpViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun PointLookUpBody() {

  val viewModel: LookUpViewModel = getViewModel() //INJECTED //make sure you get the viewModel function from the koin lib

  val listOfEvents by viewModel.getListOfEvents().observeAsState(listOf())

  val selectedEvent by viewModel.getSelectedEvent().observeAsState(viewModel.sampleFirstEvent)

  val selectedSex by viewModel.getSelectedSex().observeAsState(AthleticsSex.Male)
  val selectedDoor by viewModel.getSelectedDoor().observeAsState(AthleticsDoor.Indoor)

  val performanceString by viewModel.getPerformanceString().observeAsState("0.0")
  val performancePoints by viewModel.getPerformancePoints().observeAsState("0")

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

    //TODO performance input should handle different inputs - also hours minutes seconds, tenths
    PerformanceInput(modifier = Modifier.padding(bottom = 8.dp), performanceString) {
      viewModel.updatePerformanceString(it)
    }

    PointsDisplay(performancePoints)

    CategorySelector(
      selectedSex,
      selectedDoor,
      onSexSelectionChange = {
        viewModel.updateUIWithNewSexCategory(findById(it.id))
      },
      onDoorSelectionChange = {
        viewModel.updateUIWithNewDoorCategory(findById(it.id))
      }
    )

    CustomDivider()

    EventListDisplayer(listOfEvents, selectedEvent) {
      viewModel.newEventSelected(it)
    }
  }
}

@Composable
@Preview
fun PointLookUpBodyPreview() {
  MaterialTheme {
//    PointLookUpBody()
  }
}







