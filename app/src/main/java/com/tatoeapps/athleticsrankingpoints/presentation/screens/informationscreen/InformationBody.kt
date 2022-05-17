package com.tatoeapps.athleticsrankingpoints.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun InformationBody() {
  Column(
    modifier = Modifier
      .padding(16.dp)
      .verticalScroll(rememberScrollState())
      .semantics { contentDescription = "Information Screen" }

  ) {
    Text(
      text = "About World Rankings",
      style = MaterialTheme.typography.h2,
      modifier = Modifier
        .align(Alignment.CenterHorizontally)
        .padding(bottom = 16.dp)
    )
    Text(
      text = "The current qualification system for World Championships, the Olympics and European championships",
      style = MaterialTheme.typography.h3,
      modifier = Modifier
        .align(Alignment.CenterHorizontally)
        .padding(bottom = 16.dp)
    )
    Text(
      text = "Qualifications will be awarded to  the higher ranked athletes in the world ranking system. Each athlete will have a score based" +
          "on their performances over the last 2 years, which takes in account their score, their position and the importance of the championship.",
      style = MaterialTheme.typography.body1,
      modifier = Modifier
        .align(Alignment.CenterHorizontally)
        .padding(bottom = 16.dp)
    )
    Text(
      text = "This app tries to make calculating and storing athletes World Rankings score easy. Use the Point Look up tab to check the " +
          "number of points an event time or distance equates to. Use the Simulator tab to calculate an athlete's World Ranking Score and to save" +
          " it so you can access it later.",
      style = MaterialTheme.typography.body1,
      modifier = Modifier
        .align(Alignment.CenterHorizontally)
        .padding(bottom = 16.dp)
    )
  }
}

@Preview
@Composable
fun previewThis() {
  Surface(modifier = Modifier.fillMaxSize(),color = Color.White) {
    InformationBody()
  }
}