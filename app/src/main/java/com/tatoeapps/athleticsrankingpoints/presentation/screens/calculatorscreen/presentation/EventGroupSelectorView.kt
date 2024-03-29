package com.tatoeapps.athleticsrankingpoints.presentation.screens.calculatorscreen.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tatoeapps.athleticsrankingpoints.R
import com.tatoeapps.athleticsrankingpoints.domain.interfaces.SelectableIdentifiable
import com.tatoeapps.athleticsrankingpoints.domain.interfaces.findById
import com.tatoeapps.athleticsrankingpoints.domain.models.AthleticsEvent
import com.tatoeapps.athleticsrankingpoints.domain.models.AthleticsSex
import com.tatoeapps.athleticsrankingpoints.domain.models.EventGroup
import com.tatoeapps.athleticsrankingpoints.presentation.components.*
import com.tatoeapps.athleticsrankingpoints.presentation.screens.calculatorscreen.EventGroupSelectorViewModel
import com.tatoeapps.athleticsrankingpoints.presentation.theme.AthleticsRankingPointsTheme
import com.tatoeapps.athleticsrankingpoints.presentation.theme.beige
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

  Column(Modifier
    .background(color = AthleticsRankingPointsTheme.colors.backgroundScreen)
    .padding(16.dp)) {
    Text(
      text = stringResource(id = R.string.calculator_subtitle).uppercase(),
      style = AthleticsRankingPointsTheme.typography.title3.beige,
      modifier = Modifier
        .align(Alignment.Start)
    )

    Spacer(modifier = Modifier.height(8.dp))

    SelectedEventGroupInfo(modifier = Modifier.padding(bottom = 16.dp), eventGroup = selectedEventGroup)

    SexRadioButtonSet(selectedSex = selectedSex, onSexSelectionChange = {
      viewModel.updateUIWithNewSexCategory(findById(it.id))
      }
    )

    CustomDivider()

    EventGroupListDisplayer(modifier = Modifier
      .weight(1f),
      listOfEventGroups, selectedEventGroup) {
      viewModel.newEventSelected(it)
    }

    Spacer(modifier = Modifier.height(16.dp))

    Box(modifier = Modifier.align(Alignment.End) ){
      CustomButton(text = stringResource(id = R.string.continue_button), onButtonTap = {
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
    buttonOptions = AthleticsEvent.listSexOptions,
    borderColorDisabled = AthleticsRankingPointsTheme.colors.textWhite.copy(alpha = 0.5f),
    verticalPadding = 12.dp
  ) {
    onSexSelectionChange(it)
  }
}

