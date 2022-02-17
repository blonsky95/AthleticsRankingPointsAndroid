package com.example.athleticsrankingpoints.presentation.screens.lookupscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.athleticsrankingpoints.domain.interfaces.findById
import com.example.athleticsrankingpoints.domain.models.AthleticsDoor
import com.example.athleticsrankingpoints.domain.models.AthleticsEventType
import com.example.athleticsrankingpoints.domain.models.AthleticsSex
import com.example.athleticsrankingpoints.domain.models.PerformanceUnitsAware
import com.example.athleticsrankingpoints.presentation.components.*
import com.example.athleticsrankingpoints.presentation.theme.AthleticsRankingPointsTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun PointLookUpBody() {

  val viewModel: LookUpViewModel = getViewModel() //INJECTED //make sure you get the viewModel function from the koin lib

  val listOfEvents by viewModel.getListOfEvents().observeAsState(listOf())

  val selectedEvent by viewModel.getSelectedEvent().observeAsState(viewModel.sampleFirstEvent)

  val selectedSex by viewModel.getSelectedSex().observeAsState(AthleticsSex.Male)
  val selectedDoor by viewModel.getSelectedDoor().observeAsState(AthleticsDoor.Indoor)

  val performanceUnitsAware by viewModel.getPerformanceUnitsAware().observeAsState(PerformanceUnitsAware.getDefault())

  val performancePoints by viewModel.getPerformancePoints().observeAsState("0")

  Column(Modifier
    .background(color = AthleticsRankingPointsTheme.colors.backgroundSecondary)
    .padding(16.dp)
  ) {

    Text(
      text = selectedEvent.sName,
      style = AthleticsRankingPointsTheme.typography.title1,
      modifier = Modifier
        .align(Start)
    )

    Spacer(modifier = Modifier.height(8.dp))

    PerformanceInput(
      modifier = Modifier.fillMaxWidth(),
      modifierForInputUnit = Modifier.width(80.dp),
      performanceUnitsAware = performanceUnitsAware) {newPerformance ->
      viewModel.updatePerformanceUnitsAware(newPerformance)
    }
    Spacer(modifier = Modifier.height(8.dp))
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







