package com.example.athleticsrankingpoints.presentation.screens.performancesscreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.athleticsrankingpoints.data.entity.RankingScorePerformanceData
import com.example.athleticsrankingpoints.presentation.screens.lookupscreen.LookUpViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun PerformancesBody() {

  val viewModel: PerformancesViewModel = getViewModel()
  val listOfPerformances by viewModel.listOfPerformances.observeAsState(listOf())
//  val stubPerformancesData = RankingScorePerformanceData.getSampleDataList(10)
  Column (
    modifier = Modifier.padding(16.dp)
  ){
    Text(
      text = "Performances",
      style = MaterialTheme.typography.h2,
      modifier = Modifier
        .align(Alignment.Start)
        .padding(bottom = 8.dp)
    )
    Text(
      text = "Tap performances to view in more detail",
      style = MaterialTheme.typography.body1,
      modifier = Modifier
        .align(Alignment.Start)
        .align(Alignment.CenterHorizontally)
    )
    Spacer(modifier = Modifier.height(8.dp))
    Divider(
      modifier = Modifier
        .fillMaxWidth()
        .height(1.dp),
      color = Color.White
    )
    LazyColumn(
      modifier = Modifier
        .padding(4.dp)

    ) {
      items(listOfPerformances) { performance ->
        PerformancesListItem(performanceData = performance)
      }

    }
  }

}

@Composable
fun PerformancesListItem(modifier: Modifier = Modifier, performanceData: RankingScorePerformanceData) {
  Column(modifier = modifier) {
    Row (modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween) {
      Column() {
        Text(
          text = performanceData.name,
          style = MaterialTheme.typography.body1,
          color = Color.White,
          modifier = Modifier.align(Alignment.Start)
        )
        Text(
          text = performanceData.eventGroup.sName,
          style = MaterialTheme.typography.body1,
          color = Color.White,
          modifier = Modifier.align(Alignment.Start)
        )
      }
      Text(
        text = performanceData.rankingScore,
        style = MaterialTheme.typography.h5,
        color = Color.White,
        modifier = Modifier.align(Alignment.CenterVertically)
      )
    }
    Spacer(modifier = Modifier.height(8.dp))
    Divider(
      modifier = Modifier
        .fillMaxWidth()
        .height(1.dp),
      color = Color.White
    )
  }

}

@Preview
@Composable
fun PreviewThis() {
  PerformancesListItem(performanceData = RankingScorePerformanceData.getSampleData())
}