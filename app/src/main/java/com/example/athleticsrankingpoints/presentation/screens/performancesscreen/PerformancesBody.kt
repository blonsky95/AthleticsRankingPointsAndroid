package com.example.athleticsrankingpoints.presentation.screens.performancesscreen

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.athleticsrankingpoints.data.entities.RankingScorePerformanceData
import com.example.athleticsrankingpoints.domain.models.EventGroup
import com.example.athleticsrankingpoints.presentation.theme.*
import com.example.athleticsrankingpoints.upperCaseFirstLetter
import org.koin.androidx.compose.getViewModel

@Composable
fun PerformancesBody(
  onPerformanceClick: (EventGroup, String) -> Unit
) {

  val viewModel: PerformancesViewModel = getViewModel()
  val listOfPerformances by viewModel.getListOfPerformances().observeAsState(listOf())
  val searchText by viewModel.getSearchText().observeAsState(initial = "")

  Column (
    modifier = Modifier.padding(16.dp)
  ){
    Text(
      text = "Performances".uppercase(),
      style = AthleticsRankingPointsTheme.typography.title3.beige,
      modifier = Modifier
        .align(Alignment.Start)
    )
    Spacer(modifier = Modifier.height(8.dp))
    TextField(
      modifier = Modifier
        .fillMaxWidth(),
      value = searchText,
      onValueChange = {
        viewModel.updateSearchText(it)
      }
    )
    Spacer(modifier = Modifier.height(8.dp))
    Divider(color = AthleticsRankingPointsTheme.colors.textWhite.copy(alpha = 0.5f), thickness = 1.dp)
    if (!listOfPerformances.isNullOrEmpty()) {
      LazyColumn(
        modifier = Modifier
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
          style = AthleticsRankingPointsTheme.typography.text3.grey
        )
      }
    }
  }
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
          text = performanceData.name,
          style = AthleticsRankingPointsTheme.typography.text1.white,
          modifier = Modifier.align(Alignment.Start)
        )
        Text(
          text = performanceData.eventGroup.sName,
          style = AthleticsRankingPointsTheme.typography.text1.white,
          modifier = Modifier.align(Alignment.Start)
        )
        Text(
          text = performanceData.eventGroup.sSex.name,
          style = AthleticsRankingPointsTheme.typography.text1.white,
          modifier = Modifier.align(Alignment.Start)
        )
      }
      Text(
        text = performanceData.rankingScore,
        style = AthleticsRankingPointsTheme.typography.text1.white.bold,
        modifier = Modifier.align(Alignment.CenterVertically)
      )
    }
    Spacer(modifier = Modifier.height(8.dp))
    Divider(color = AthleticsRankingPointsTheme.colors.textWhite.copy(alpha = 0.5f), thickness = 1.dp)
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