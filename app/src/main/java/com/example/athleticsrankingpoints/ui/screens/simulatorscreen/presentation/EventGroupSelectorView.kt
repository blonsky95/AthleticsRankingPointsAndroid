package com.example.athleticsrankingpoints.ui.simulatorscreen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.athleticsrankingpoints.domain.AthleticsEvent
import com.example.athleticsrankingpoints.domain.EventGroup
import com.example.athleticsrankingpoints.ui.components.CustomButton
import com.example.athleticsrankingpoints.ui.components.CustomTwoRadioButtonGroup
import com.example.athleticsrankingpoints.ui.components.EventGroupListDisplayer
import com.example.athleticsrankingpoints.ui.components.EventGroupSummary
import com.example.athleticsrankingpoints.ui.screens.simulatorscreen.EventGroupSelectorViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun EventGroupSelectorBody(
  onNextClick: (EventGroup) -> Unit = {}
){

  val eventGroupSelectorViewModel: EventGroupSelectorViewModel = getViewModel() //INJECTED //make sure you get the viewModel function from the koin lib

  val listOfEventGroups by eventGroupSelectorViewModel.getListOfEventGroups().observeAsState(listOf())

  val selectedEventGroup by eventGroupSelectorViewModel.getSelectedEventGroup().observeAsState(eventGroupSelectorViewModel.sampleFirstEventGroup)

  val selectedSex by eventGroupSelectorViewModel.getSelectedSex().observeAsState(AthleticsEvent.sexMale)

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
      eventGroupSelectorViewModel.updateUIWithNewSexCategory(it)
      }
    )

    EventGroupListDisplayer(modifier = Modifier
      .weight(1f)
      .padding(top = 8.dp, bottom = 8.dp), listOfEventGroups, selectedEventGroup) {
      eventGroupSelectorViewModel.newEventSelected(it)
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

