package com.example.athleticsrankingpoints.presentation.screens.homescreen

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
import com.example.athleticsrankingpoints.R
import com.example.athleticsrankingpoints.domain.interfaces.findById
import com.example.athleticsrankingpoints.domain.models.AthleticsDoor
import com.example.athleticsrankingpoints.domain.models.AthleticsSex
import com.example.athleticsrankingpoints.domain.models.PerformanceUnitsAware
import com.example.athleticsrankingpoints.presentation.components.*
import com.example.athleticsrankingpoints.presentation.theme.AthleticsRankingPointsTheme
import com.example.athleticsrankingpoints.presentation.theme.beige
import com.example.athleticsrankingpoints.presentation.theme.lilac
import com.example.athleticsrankingpoints.presentation.theme.white
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
      customInputColors =
      CustomInputColors(
        unitText = white,
        valueText = white,
        valueTextBackground = Color.Transparent,
        unitTextBackground = lilac,
        borderColor = lilac.copy(alpha = 0.5f),
        brushColor = white.copy(alpha = 0.8f)
      ),
      performanceUnitsAware = performanceUnitsAware) {newPerformance ->
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
    EventListDisplayer(listOfEvents = listOfEvents, selectedEvent= selectedEvent) {
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







