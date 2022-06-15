package com.tatoeapps.athleticsrankingpoints.presentation.screens.savedscreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tatoeapps.athleticsrankingpoints.R
import com.tatoeapps.athleticsrankingpoints.data.entities.RankingScorePerformanceData
import com.tatoeapps.athleticsrankingpoints.domain.models.EventGroup
import com.tatoeapps.athleticsrankingpoints.presentation.components.CustomInputColors
import com.tatoeapps.athleticsrankingpoints.presentation.components.CustomInputField
import com.tatoeapps.athleticsrankingpoints.presentation.theme.AthleticsRankingPointsTheme
import com.tatoeapps.athleticsrankingpoints.presentation.theme.bold
import com.tatoeapps.athleticsrankingpoints.presentation.theme.navyBlue
import com.tatoeapps.athleticsrankingpoints.presentation.theme.textBlue
import org.koin.androidx.compose.getViewModel

@Composable
fun PerformancesBody(
  onPerformanceClick: (EventGroup, String) -> Unit
) {

  val viewModel: PerformancesViewModel = getViewModel()
  val listOfPerformances by viewModel.getListOfPerformances().observeAsState(listOf())
  val searchText by viewModel.getSearchText().observeAsState(initial = "")

  Column (
    modifier = Modifier
      .background(
        color = AthleticsRankingPointsTheme.colors.backgroundComponent,
      )
      .padding(16.dp)
  ){
    Text(
      text = stringResource(id = R.string.saved_performances).uppercase(),
      style = AthleticsRankingPointsTheme.typography.title3.navyBlue,
      modifier = Modifier
        .align(Alignment.Start)
    )
    Spacer(modifier = Modifier.height(8.dp))
    PerformancesSearchBar(searchText) {
      viewModel.updateSearchText(it)
    }
    Spacer(modifier = Modifier.height(8.dp))
    Divider(color = AthleticsRankingPointsTheme.colors.backgroundScreen, thickness = 1.dp)
    if (!listOfPerformances.isNullOrEmpty()) {
      LazyColumn(
        modifier = Modifier
          .fillMaxSize()
          .padding(4.dp)
      ) {
        items(listOfPerformances) { performance ->
          PerformancesListItem(performanceData = performance, onPerformanceClick = onPerformanceClick)
        }
      }
    } else {
      Box(modifier = Modifier.fillMaxSize()) {
        Text(
          modifier = Modifier.align(Alignment.Center),
          textAlign = TextAlign.Center,
          text = "No performances found",
          style = AthleticsRankingPointsTheme.typography.text4.navyBlue
        )
      }
    }
  }
}

@Composable
private fun PerformancesSearchBar(searchText: String, onSearchChange: (String) -> Unit) {

  CustomInputField(
    customInputColors = CustomInputColors(),
    value = searchText,
    canFillMaxWidth = true,
    minWidthInDp = 192.dp,
    hint = "Search here...",
    onValueChange = onSearchChange
  )
//  TextField(
//    modifier = Modifier
//      .fillMaxWidth(),
//    value = searchText,
//    onValueChange = {
//      onSearchChange(it)
//    }
//  )
}

@Composable
fun PerformancesListItem(
  modifier: Modifier = Modifier,
  performanceData: RankingScorePerformanceData,
  onPerformanceClick: (EventGroup, String) -> Unit
) {
  Column(modifier = modifier) {
    Row (modifier = Modifier
      .fillMaxWidth()
      .clickable { onPerformanceClick(performanceData.eventGroup, performanceData.name) },
      horizontalArrangement = Arrangement.SpaceBetween) {
      Column() {
        Text(
          text = performanceData.name.uppercase(),
          style = AthleticsRankingPointsTheme.typography.title3.navyBlue,
          modifier = Modifier.align(Alignment.Start)
        )
        Text(
          text = performanceData.eventGroup.sName,
          style = AthleticsRankingPointsTheme.typography.text3.navyBlue,
          modifier = Modifier.align(Alignment.Start)
        )
        Text(
          text = performanceData.eventGroup.sSex.name,
          style = AthleticsRankingPointsTheme.typography.text3.navyBlue,
          modifier = Modifier.align(Alignment.Start)
        )
      }
      Text(
        text = performanceData.rankingScore,
        style = AthleticsRankingPointsTheme.typography.title3.textBlue.bold,
        modifier = Modifier.align(Alignment.CenterVertically)
      )
    }
    Spacer(modifier = Modifier.height(8.dp))
    Divider(color = AthleticsRankingPointsTheme.colors.backgroundScreen, thickness = 1.dp)
  }
}

@Preview
@Composable
fun PreviewThis() {
  PerformancesListItem(performanceData = RankingScorePerformanceData.getSampleData(), onPerformanceClick =
  { eventGroup, performanceName ->
    Log.d("TAG", "${eventGroup.sName} $performanceName")
  }
  )
}