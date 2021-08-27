package com.example.athleticsrankingpoints.navigation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.athleticsrankingpoints.WorldRankingScreen
import androidx.navigation.compose.composable
import androidx.compose.ui.Modifier
import com.example.athleticsrankingpoints.ui.InformationBody
import com.example.athleticsrankingpoints.ui.lookupscreen.PointLookUpBody

@Composable
fun WorldRankingNavHost(
  navHostController: NavHostController,
  modifier: Modifier
) {
  androidx.navigation.compose.NavHost(
    navController = navHostController,
    startDestination = WorldRankingScreen.PointLookUp.name,
    modifier = modifier
  ) {

    composable(route = WorldRankingScreen.PointLookUp.name) {
      PointLookUpBody()
    }
    composable(route = WorldRankingScreen.Simulator.name) {
      Text("I'm simulator")
    }
    composable(route = WorldRankingScreen.Information.name) {
      InformationBody()
    }
  }
}