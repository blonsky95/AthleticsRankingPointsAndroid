package com.example.athleticsrankingpoints.presentation.simulatorscreen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.athleticsrankingpoints.domain.models.AthleticsEvent
import com.example.athleticsrankingpoints.domain.models.EventGroup
import com.example.athleticsrankingpoints.presentation.components.CustomButton
import com.example.athleticsrankingpoints.presentation.components.CustomTwoRadioButtonGroup
import com.example.athleticsrankingpoints.presentation.components.EventGroupListDisplayer
import com.example.athleticsrankingpoints.presentation.components.EventGroupSummary
import com.example.athleticsrankingpoints.presentation.screens.simulatorscreen.EventGroupSelectorViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun EventGroupSelectorBody(
  onNextClick: (EventGroup) -> Unit = {}
){

  val viewModel: EventGroupSelectorViewModel = getViewModel() //INJECTED //make sure you get the viewModel function from the koin lib

  val listOfEventGroups by viewModel.getListOfEventGroups().observeAsState(listOf())

  val selectedEventGroup by viewModel.getSelectedEventGroup().observeAsState(viewModel.sampleFirstEventGroup)

  val selectedSex by viewModel.getSelectedSex().observeAsState(AthleticsEvent.sexMale)

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
      viewModel.updateUIWithNewSexCategory(it)
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
fun SexRadioButtonSet(selectedSex: String, onSexSelectionChange: (String) -> Unit) {
  CustomTwoRadioButtonGroup(
    modifier = Modifier.fillMaxWidth(),
    selectedOption = selectedSex,
    buttonOptions = AthleticsEvent.listSexOptions
  ) {
    onSexSelectionChange(it)
  }
}

