package com.example.athleticsrankingpoints.presentation.screens.simulatorscreen.presentation

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.athleticsrankingpoints.domain.interfaces.SelectableIdentifiable
import com.example.athleticsrankingpoints.domain.interfaces.findById
import com.example.athleticsrankingpoints.domain.models.AthleticsEvent
import com.example.athleticsrankingpoints.domain.models.AthleticsSex
import com.example.athleticsrankingpoints.domain.models.EventGroup
import com.example.athleticsrankingpoints.presentation.components.CustomButton
import com.example.athleticsrankingpoints.presentation.components.CustomTwoRadioButtonGroup
import com.example.athleticsrankingpoints.presentation.components.EventGroupListDisplayer
import com.example.athleticsrankingpoints.presentation.components.EventGroupSummary
import com.example.athleticsrankingpoints.presentation.screens.simulatorscreen.EventGroupSelectorViewModel
import org.koin.androidx.compose.getViewModel

@ExperimentalAnimationApi
@Composable
fun EventGroupSelectorView(
  onNextClick: (EventGroup) -> Unit = {}
){

  val viewModel: EventGroupSelectorViewModel = getViewModel() //INJECTED //make sure you get the viewModel function from the koin lib

  val listOfEventGroups by viewModel.getListOfEventGroups().observeAsState(listOf())

  val selectedEventGroup by viewModel.getSelectedEventGroup().observeAsState(viewModel.sampleFirstEventGroup)

  val selectedSex by viewModel.getSelectedSex().observeAsState(AthleticsSex.Male)

  Column(Modifier.padding(16.dp)) {
    Text(
      text = "Select your Event Group",
      style = MaterialTheme.typography.h4,
      modifier = Modifier
        .align(Alignment.Start)
        .padding(bottom = 8.dp)
    )

    EventGroupSummary(modifier = Modifier.padding(bottom = 16.dp),eventGroup = selectedEventGroup)

    SexRadioButtonSet(selectedSex = selectedSex, onSexSelectionChange = {
      viewModel.updateUIWithNewSexCategory(findById(it.id))
      }
    )

    EventGroupListDisplayer(modifier = Modifier
      .weight(1f)
      .padding(top = 8.dp, bottom = 8.dp), listOfEventGroups, selectedEventGroup) {
      viewModel.newEventSelected(it)
    }

    Box(modifier = Modifier.align(Alignment.End) ){
      CustomButton(text = "NEXT", onButtonTap = {
        Log.d("NAVIGATION SIM", "GO TO NEXT")
        onNextClick(selectedEventGroup)
      })
    }

  }
}

@Composable
fun SexRadioButtonSet(selectedSex: AthleticsSex, onSexSelectionChange: (SelectableIdentifiable) -> Unit) {
  CustomTwoRadioButtonGroup(
    modifier = Modifier.fillMaxWidth(),
    selectedOption = selectedSex,
    buttonOptions = AthleticsEvent.listSexOptions
  ) {
    onSexSelectionChange(it)
  }
}

