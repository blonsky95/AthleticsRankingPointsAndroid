package com.example.athleticsrankingpoints.presentation.screens.simulatorscreen.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.example.athleticsrankingpoints.Strings.AttentionText
import com.example.athleticsrankingpoints.Strings.CancelText
import com.example.athleticsrankingpoints.Strings.DeletePerformanceDialogText
import com.example.athleticsrankingpoints.Strings.NameOverwritePerformanceDialogText
import com.example.athleticsrankingpoints.Strings.YesText
import com.example.athleticsrankingpoints.data.entities.RankingScorePerformanceData
import com.example.athleticsrankingpoints.domain.models.EventGroup
import com.example.athleticsrankingpoints.presentation.components.*
import com.example.athleticsrankingpoints.presentation.screens.simulatorscreen.EventGroupSimulatorViewModel
import com.example.athleticsrankingpoints.presentation.screens.simulatorscreen.SimulatorDataModel
import com.example.athleticsrankingpoints.presentation.theme.AthleticsRankingPointsTheme
import org.koin.core.parameter.parametersOf


@ExperimentalAnimationApi
@Composable
fun EventGroupSimulatorView(navigateToSavedPerformances: () -> Unit, eventGroupName: String, loadPerformanceName: String) {

  val viewModel: EventGroupSimulatorViewModel = getViewModel(parameters = {parametersOf(SimulatorDataModel(eventGroupName, loadPerformanceName))})//INJECTED

  val title by viewModel.getTitle().observeAsState("")

  val eventGroup by viewModel.getSelectedEventGroup().observeAsState()

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
  val showErrorValidatingDialog by viewModel.getShowErrorValidatingDialog().observeAsState(null)

  val isLoaded = loadPerformanceName!=RankingScorePerformanceData.NEW_ENTRY

  Scaffold(
    modifier = Modifier
      .fillMaxSize(),
    scaffoldState = scaffoldState
  ) {
    //todo refactor this into composables
    if (showDeleteDialog) {
      DialogWindow(hideDialog = {viewModel.hideDeleteDialog()}, dialogText = DeletePerformanceDialogText) {
        viewModel.confirmDeletePerformance()
        navigateToSavedPerformances()
      }
    }
    if (showNameOverwriteDialog) {
      DialogWindow(hideDialog = {viewModel.hideNameOverwriteDialog()}, dialogText = NameOverwritePerformanceDialogText) {
        viewModel.confirmUpdatePerformance()
      }
    }
    if (showErrorValidatingDialog!=null) {
      AlertWindow(hideDialog = {viewModel.hideErrorValidateDialog()}, dialogText = showErrorValidatingDialog?:"Wops")
    }

    if (snackBarModel.isShowing) {
      LaunchedEffect(snackBarModel) {
        scaffoldState.snackbarHostState.showSnackbar(snackBarModel.text)
      }
    }

    Column(
      modifier = Modifier
        .background(color = AthleticsRankingPointsTheme.colors.backgroundComponent)
        .padding(16.dp)
        .semantics { contentDescription = "Simulator Screen" }

    ) {
      Spacer(modifier = Modifier.height(4.dp))
      MyPerformanceTitleTextField(title = title, onValueChange = {viewModel.updateTitle(it)}, hint = "Title")
      Spacer(modifier = Modifier.height(4.dp))
      EventGroupSummary(modifier = Modifier.padding(bottom = 16.dp),eventGroup = eventGroup?: EventGroup.getSampleEventGroup())
      PerformancesSimulatorList(
        modifier = Modifier
          .weight(1f)
          .background(color = AthleticsRankingPointsTheme.colors.backgroundScreen, shape = RoundedCornerShape(4.dp)),
        perfList = performanceList,
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

      Row (Modifier.padding(top = 16.dp)){
        Box(modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 8.dp)) {
          Text(modifier = Modifier
            .align(Alignment.CenterStart),
            style = MaterialTheme.typography.body1,
            text = "Ranking Score:")

          Text(modifier = Modifier
            .align(Alignment.CenterEnd),
            style = MaterialTheme.typography.body1,
            text = viewModel.getRankingScore(performancePointsList,placementPointsList, windsPointsList))
        }
      }
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween.takeIf { isLoaded } ?: Arrangement.End
      ) {

        if (isLoaded) {
          CustomButton(
            text = "DELETE") {
            viewModel.deleteButtonPressed()
          }
        }
        CustomButton(text = "SAVE") {
          viewModel.saveButtonPressed()
        }

      }

    }
  }
}

@Composable
fun MyPerformanceTitleTextField(title:String, onValueChange : (String) -> Unit, hint:String) {
  BasicTextField(
    value = title,
    onValueChange = {
      onValueChange(it)
    },
    modifier = Modifier
      .background(AthleticsRankingPointsTheme.colors.textWhite, shape = RoundedCornerShape(4.dp))
      .padding(8.dp)
      .widthIn(1.dp, Dp.Infinity)
      .heightIn(1.dp, Dp.Infinity),
    textStyle = TextStyle(color = Color.Black, fontSize = 18.sp),
    cursorBrush = SolidColor(Color.Black),
    decorationBox = { innerTextField ->
      if (title.isEmpty()) {
        Text(
          text = hint,
          style = TextStyle(color = Color.LightGray, fontWeight = FontWeight.Light, fontSize = 18.sp)
        )
      }
      innerTextField()
    },
  )
}

@Composable
fun DialogWindow(hideDialog: () -> Unit, dialogText: String = "Missing text", onConfirmDialog: () -> Unit) {
    AlertDialog(
      onDismissRequest = {
        hideDialog()
      },
      title = {
        Text(AttentionText)
      },
      text = {
        Text(dialogText)
      },
      confirmButton = {
        Button(
          onClick = {
            onConfirmDialog()
            hideDialog()
          },
        ) {
          Text(YesText)
        }
      },
      dismissButton = {
        Button(
          onClick = {
            hideDialog()
          },
        ) {
          Text(CancelText)
        }
      },
    )
  }

@Composable
fun AlertWindow(hideDialog: () -> Unit, dialogText: String = "Missing text") {
  AlertDialog(
    onDismissRequest = {
      hideDialog()
    },
    title = {
      Text(AttentionText)
    },
    text = {
      Text(dialogText)
    },
    confirmButton = {
      Button(
        onClick = {
          hideDialog()
        },
      ) {
        Text(CancelText)
      }
    }
  )
}

@ExperimentalAnimationApi
@Preview
@Composable
fun PreviewEventGroupSimulatorView() {
  AthleticsRankingPointsTheme() {
    EventGroupSimulatorView(navigateToSavedPerformances = {}, eventGroupName = EventGroup.getSampleEventGroup().sName, loadPerformanceName = "")
  }
}



