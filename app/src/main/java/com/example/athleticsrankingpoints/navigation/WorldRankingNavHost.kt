package com.example.athleticsrankingpoints.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.example.athleticsrankingpoints.WorldRankingScreen
import com.example.athleticsrankingpoints.domain.EventGroup
import com.example.athleticsrankingpoints.ui.eventgroupselector.simulator.EventGroupSimulatorBody
import com.example.athleticsrankingpoints.ui.InformationBody
import com.example.athleticsrankingpoints.ui.eventgroupselector.EventGroupSelectorBody
import com.example.athleticsrankingpoints.ui.lookupscreen.PointLookUpBody

//So what we are going to do here is, the NavHost wont be injected, or passed around through screens.
//All the navigations will be here
//The main tabs are accesible from top bar
//If you are going deeper into a tab nav scheme, a callback function will be sent as parameter to that tab nav.
//Example - if selectorbody will take you to selectorsimulator, onNextClick will carry this implementation
//If it gets more and more complicated, it will be injected.

@Composable
fun WorldRankingNavHost(
  navHostController: NavHostController,
  modifier: Modifier
) {

  NavHost(
    navController = navHostController,
    startDestination = WorldRankingScreen.PointLookUp.name,
    modifier = modifier
  ) {
    composable(route = WorldRankingScreen.PointLookUp.name) {
      PointLookUpBody()
    }
    composable(route = WorldRankingScreen.Simulator.name) {
      EventGroupSelectorBody(
        onNextClick = { eventGroup ->
          navigateToEventGroupSimulator(navHostController, eventGroup)
        })
    }
    composable(route = WorldRankingScreen.Information.name) {
      InformationBody()
    }
    composable(
      route = "EventGroupSimulator/{eventGroupName}",
      arguments = listOf(
        navArgument("eventGroupName") {
          type = NavType.StringType
        }
      )
    )
    {
      entry ->
      val eventGroupName = entry.arguments?.getString("eventGroupName")
      EventGroupSimulatorBody(eventGroupName = eventGroupName ?: "empty" )
    }
  }
}

private fun navigateToEventGroupSimulator(
  navController: NavHostController,
  eventGroup: EventGroup
) {
  navController.navigate("EventGroupSimulator/${eventGroup.sName}")
}