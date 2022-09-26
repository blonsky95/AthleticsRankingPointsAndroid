package com.tatoeapps.athleticsrankingpoints.presentation.screens.homescreen

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.tatoeapps.athleticsrankingpoints.R
import com.tatoeapps.athleticsrankingpoints.domain.interfaces.findById
import com.tatoeapps.athleticsrankingpoints.domain.models.AthleticsDoor
import com.tatoeapps.athleticsrankingpoints.domain.models.AthleticsSex
import com.tatoeapps.athleticsrankingpoints.domain.models.PerformanceUnitsAware
import com.tatoeapps.athleticsrankingpoints.presentation.components.*
import com.tatoeapps.athleticsrankingpoints.presentation.theme.AthleticsRankingPointsTheme
import com.tatoeapps.athleticsrankingpoints.presentation.theme.beige
import com.tatoeapps.athleticsrankingpoints.presentation.theme.lilac
import com.tatoeapps.athleticsrankingpoints.presentation.theme.white
import org.koin.androidx.compose.getViewModel

@Composable
fun PointLookUpBody() {

  val viewModel: HomeViewModel = getViewModel() //INJECTED //make sure you get the viewModel function from the koin lib

  val listOfEvents by viewModel.getListOfEvents().observeAsState(listOf())

  val selectedEvent by viewModel.getSelectedEvent().observeAsState(viewModel.sampleFirstEvent)

  val selectedSex by viewModel.getSelectedSex().observeAsState(AthleticsSex.Male)
  val selectedDoor by viewModel.getSelectedDoor().observeAsState(AthleticsDoor.Indoor)

  val performanceUnitsAware by viewModel.getPerformanceUnitsAware().observeAsState(PerformanceUnitsAware.getDefault())

  val performancePoints by viewModel.getPerformancePoints().observeAsState("0")
  CustomBackground(image = R.drawable.track_clipart, modifier = Modifier.zIndex(1f))
  Column(Modifier
    .padding(16.dp)
  ) {
    Text(
      text = selectedEvent.sName,
      style = AthleticsRankingPointsTheme.typography.title2.beige,
      modifier = Modifier
        .align(Start)
    )
    PointsDisplay(performancePoints)
    Spacer(modifier = Modifier.height(8.dp))
    PerformanceInput(
      modifier = Modifier
        .padding(vertical = 4.dp, horizontal = 4.dp),
      colorStyle = InputFieldColourStyle.HomeInputFieldColour,
      performanceUnitsAware = performanceUnitsAware
    ) { newPerformance ->
      viewModel.updatePerformanceUnitsAware(newPerformance)
    }
    Spacer(modifier = Modifier.height(8.dp))
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
    Spacer(modifier = Modifier.height(6.dp))
    EventListDisplayer(listOfEvents = listOfEvents, selectedEvent = selectedEvent) {
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







