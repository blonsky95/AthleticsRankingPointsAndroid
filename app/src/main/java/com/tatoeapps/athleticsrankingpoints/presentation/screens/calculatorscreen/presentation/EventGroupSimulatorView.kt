package com.tatoeapps.athleticsrankingpoints.presentation.screens.calculatorscreen.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.getViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.tatoeapps.athleticsrankingpoints.R
import com.tatoeapps.athleticsrankingpoints.domain.models.EventGroup
import com.tatoeapps.athleticsrankingpoints.presentation.components.*
import com.tatoeapps.athleticsrankingpoints.presentation.screens.calculatorscreen.EventGroupSimulatorViewModel
import com.tatoeapps.athleticsrankingpoints.presentation.screens.calculatorscreen.SimulatorDTO
import com.tatoeapps.athleticsrankingpoints.presentation.theme.AthleticsRankingPointsTheme
import com.tatoeapps.athleticsrankingpoints.presentation.theme.navyBlue
import com.tatoeapps.athleticsrankingpoints.presentation.theme.white
import org.koin.core.parameter.parametersOf


@ExperimentalAnimationApi
@Composable
fun EventGroupSimulatorView(navigateToSavedPerformances: () -> Unit, navigateUp: () -> Unit, eventGroupName: String, loadPerformanceName: String) {

  val viewModel: EventGroupSimulatorViewModel = getViewModel(parameters = {parametersOf(SimulatorDTO(eventGroupName, loadPerformanceName))})//INJECTED

  val title by viewModel.getTitle().observeAsState("")
  val isTitleValid by viewModel.getIsTitleValid().observeAsState(true)
  val isTitleInEditMode by viewModel.getIsTitleInEditMode().observeAsState(true)

  val eventGroup by viewModel.getSelectedEventGroup().observeAsState()

  val rankingScore by viewModel.getRankingScore().observeAsState("0")

  val performanceList by viewModel.getListOfPerformances().observeAsState(listOf())
  val performancePointsList by viewModel.getListOfPerformancePoints().observeAsState(listOf())
  val windsList by viewModel.getListOfWinds().observeAsState(listOf())
  val windsPointsList by viewModel.getListOfWindsPoints().observeAsState(listOf())
  val selectedEventsList by viewModel.getListOfSelectedEvents().observeAsState(listOf())
  val placementPointsList by viewModel.getListOfPlacementPoints().observeAsState(listOf())

  val snackBarModel by viewModel.snackBarModel
  val scaffoldState = rememberScaffoldState()

  val showDeleteDialog by viewModel.getShowDeleteDialog().observeAsState(false)
  val showNameOverwriteDialog by viewModel.getShowNameOverwriteDialog().observeAsState(false)
  val showErrorValidatingDialog by viewModel.getShowErrorValidatingDialog().observeAsState(-1)

  val isNewEntry = viewModel.isNewEntry

  LaunchedEffect(placementPointsList,windsPointsList, performancePointsList){
    viewModel.updateRankingScore()
  }

  Scaffold(
    modifier = Modifier
      .fillMaxSize(),
    scaffoldState = scaffoldState
  ) {
    //todo refactor this into composables
    if (showDeleteDialog) {
      DialogWindow(hideDialog = {viewModel.hideDeleteDialog()}, dialogText = stringResource(id = R.string.dialog_confirm_delete)) {
        viewModel.confirmDeletePerformance()
        navigateToSavedPerformances()
      }
    }
    if (showNameOverwriteDialog) {
      DialogWindow(hideDialog = {viewModel.hideNameOverwriteDialog()}, dialogText = stringResource(id = R.string.dialog_confirm_overwrite)) {
        viewModel.confirmUpdatePerformance()
      }
    }
    if (showErrorValidatingDialog>0) {
      AlertWindow(hideDialog = {viewModel.hideErrorValidateDialog()}, dialogText = stringResource(id = showErrorValidatingDialog))
    }

    if (snackBarModel.isShowing) {
      snackBarModel.stringResId?.let {
        stringResource(it).let { snackbarText ->
          LaunchedEffect(snackBarModel) {
            scaffoldState.snackbarHostState.showSnackbar(snackbarText)
          }
        }
      }
    }

    Column(
      modifier = Modifier
        .background(color = AthleticsRankingPointsTheme.colors.backgroundComponent)
        .semantics { contentDescription = "Simulator Screen" }

    ) {
      BackButton {
        navigateUp()
      }
      Spacer(modifier = Modifier.height(4.dp))
      PerformanceTitle(modifier= Modifier.padding(horizontal = 16.dp), title, isTitleInEditMode, isTitleValid, viewModel::updateTitle, viewModel::editTitlePressed)

      Spacer(modifier = Modifier.height(4.dp))
      SelectedEventGroupInfo(
        modifier = Modifier.padding(horizontal = 16.dp),
        contentColor = navyBlue,
        eventGroup = eventGroup?: EventGroup.getSampleEventGroup()
      )
      Spacer(modifier = Modifier.height(12.dp))
      PerformancesSimulatorList(
        modifier = Modifier
          .weight(1f)
          .padding(horizontal = 16.dp),
        performanceUnitAwareList = performanceList,
        perfPointsList = performancePointsList,
        windsList = windsList,
        windPointsList = windsPointsList,
        selectedEventsList = selectedEventsList,
        placementPointsList = placementPointsList,
        spinnerList = eventGroup!!.getAllEventsInGroup(),
        onEventChange = {index, event ->
          viewModel.updateSelectedEvent(index = index, event = event)
        },
        onPerformanceChange = { index, perf ->
          viewModel.updatePerformancesList(index = index, performance = perf)
        },
        onPlacementChange = {index, placement ->
          viewModel.updatePlacementPointsList(index = index, points = placement)
        },
        onWindChange = {index, wind ->
          viewModel.updateWindList(index = index, wind = wind)
        }

      )
      PerformanceSummarySection(rankingScore, viewModel::deleteButtonPressed, viewModel::saveButtonPressed, isNewEntry)
      Spacer(modifier = Modifier.height(16.dp))

    }
  }
}

@Composable
private fun PerformanceTitle(modifier: Modifier, title: String, isTitleInEditMode: Boolean, isTitleValid: Boolean, updateTitle: (String) -> Unit, editTitlePressed: () -> Unit) {
  Row(modifier, verticalAlignment = Alignment.CenterVertically) {
    MyPerformanceTitle(title = title, isEditMode = isTitleInEditMode, isNameValid = isTitleValid, onValueChange = { updateTitle(it) })
    Spacer(modifier = Modifier.width(24.dp))
    Image(painter = painterResource(id = if (!isTitleInEditMode) R.drawable.ic_edit else R.drawable.ic_confirm), contentDescription = "", modifier = Modifier.clickable {
      editTitlePressed()
    })
  }
}

@Composable
private fun PerformanceSummarySection(
  rankingScore: String,
  deleteButtonPressed: () -> Unit,
  saveButtonPressed: () -> Unit,
  isNewEntry: Boolean,
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .background(AthleticsRankingPointsTheme.colors.backgroundScreen)
      .padding(vertical = 12.dp, horizontal = 16.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Text(
        modifier = Modifier,
        style = AthleticsRankingPointsTheme.typography.text3.white,
        text = stringResource(id = R.string.total_score).uppercase())

      Text(modifier = Modifier,
        style = AthleticsRankingPointsTheme.typography.text2.white,
        text = rankingScore
      )
    }
    Row {
      if (!isNewEntry) {
        CustomButton(
          text = stringResource(id = R.string.delete_button).uppercase(),
          borderColor = Color.Red
        ) {
          deleteButtonPressed()
        }
      }
      Spacer(modifier = Modifier.width(12.dp))
      CustomButton(text = stringResource(id = R.string.save_button).uppercase()) {
        saveButtonPressed()
      }
    }
  }
}

@Composable
fun MyPerformanceTitle(title:String = "", isEditMode: Boolean, isNameValid: Boolean, onValueChange : (String) -> Unit) {

  if (isEditMode) {
    CustomInputField(
      customInputColors = CustomInputColors(),
      isUnitValueValid = isNameValid,
      value = title,
      canFillMaxWidth = true,
      minWidthInDp = 192.dp,
      hint = "Name",
      onValueChange = onValueChange
    )
  } else {
    Text(
      text = title,
      style = AthleticsRankingPointsTheme.typography.title3.navyBlue,
    )
  }
}



@ExperimentalAnimationApi
@Preview
@Composable
fun PreviewEventGroupSimulatorView() {
  AthleticsRankingPointsTheme() {
    EventGroupSimulatorView(navigateToSavedPerformances = {}, navigateUp = {}, eventGroupName = EventGroup.getSampleEventGroup().sName, loadPerformanceName = "")
  }
}



