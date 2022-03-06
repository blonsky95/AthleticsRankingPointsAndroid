package com.example.athleticsrankingpoints.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.athleticsrankingpoints.R
import com.example.athleticsrankingpoints.presentation.theme.AthleticsRankingPointsTheme

@Composable
fun CustomBackground(image:Int) {
  Box(modifier = Modifier
//    .background(AthleticsRankingPointsTheme.colors.background)
    .background(Color.Green)
    .fillMaxSize()
  ) {
    Image(
      modifier= Modifier.align(Alignment.BottomEnd),
      painter = painterResource(id = image), contentDescription = "app_logo")
    Image(
      modifier= Modifier.align(Alignment.TopCenter),
      painter = painterResource(id = image), contentDescription = "app_logo")
  }

}

@Preview
@Composable
fun PreviewCustomBackground() {
  CustomBackground(image = R.drawable.ic_baseline_baby_changing_station_big)
}