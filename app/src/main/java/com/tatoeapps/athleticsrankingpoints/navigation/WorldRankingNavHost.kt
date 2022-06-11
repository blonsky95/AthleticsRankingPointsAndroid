package com.tatoeapps.athleticsrankingpoints.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tatoeapps.athleticsrankingpoints.WorldRankingTabScreens
import com.tatoeapps.athleticsrankingpoints.data.entities.RankingScorePerformanceData.Companion.NEW_ENTRY
import com.tatoeapps.athleticsrankingpoints.domain.models.EventGroup
import com.tatoeapps.athleticsrankingpoints.presentation.screens.calculatorscreen.presentation.EventGroupSimulatorView
import com.tatoeapps.athleticsrankingpoints.presentation.screens.savedscreen.PerformancesBody
import com.tatoeapps.athleticsrankingpoints.presentation.screens.calculatorscreen.presentation.EventGroupSelectorView
import com.tatoeapps.athleticsrankingpoints.presentation.screens.homescreen.PointLookUpBody

//So what we are going to do here is, the NavHost wont be injected, or passed around through screens.
//All the navigations will be here
//The main tabs are accesible from top bar
//If you are going deeper into a tab nav scheme, a callback function will be sent as parameter to that tab nav.
//Example - if selectorbody will take you to selectorsimulator, onNextClick will carry this implementation
//If it gets more and more complicated, it will be injected.

@ExperimentalAnimationApi
@Composable
fun WorldRankingNavHost(
  navHostController: NavHostController,
  modifier: Modifier
) {

  NavHost(
    navController = navHostController,
    startDestination = WorldRankingTabScreens.Home.name,
    modifier = modifier
  ) {

    composable(route = WorldRankingTabScreens.Home.name) {
      PointLookUpBody()
    }

    composable(route = WorldRankingTabScreens.Calculator.name) {
      EventGroupSelectorView(
        onNextClick = { eventGroup ->
          navigateToEventGroupSimulator(navHostController, eventGroup)
        })
    }

//    composable(route = WorldRankingTabScreens.Information.name) {
//      InformationBody()
//    }

    composable(route = WorldRankingTabScreens.Saved.name) {
      PerformancesBody(
        onPerformanceClick = { eventGroup, performanceName ->
          navigateToEventGroupSimulator(navHostController, eventGroup, performanceName)
        })
    }

    composable(
      route = "EventGroupSimulator/{eventGroupName}/{performanceName}",
      arguments = listOf(
        navArgument("eventGroupName") {
          type = NavType.StringType
        },
        navArgument("performanceName") {
          type = NavType.StringType
        },
      )
    )
    {
        entry ->
      val eventGroupName = entry.arguments?.getString("eventGroupName").takeUnless { it.isNullOrEmpty() } ?: EventGroup.DEFAULT_GROUP
      val performanceName = entry.arguments?.getString("performanceName").takeUnless { it.isNullOrEmpty() } ?: NEW_ENTRY

      EventGroupSimulatorView(
        navigateToSavedPerformances = { navigateToPerformances(navHostController) },
        navigateUp = { navigateToPerformances(navHostController) },
        eventGroupName = eventGroupName,
        loadPerformanceName = performanceName
      )
    }
  }
}

private fun navigateToPerformances(navHostController: NavHostController) {
  navHostController.navigate(WorldRankingTabScreens.Saved.name)
}

private fun navigateToEventGroupSimulator(
  navController: NavHostController,
  eventGroup: EventGroup,
  performanceName: String = NEW_ENTRY
) {
  navController.navigate("EventGroupSimulator/${eventGroup.sName}/$performanceName")
}