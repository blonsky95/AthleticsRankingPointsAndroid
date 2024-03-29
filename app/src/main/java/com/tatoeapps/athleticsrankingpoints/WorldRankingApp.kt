package com.tatoeapps.athleticsrankingpoints

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tatoeapps.athleticsrankingpoints.navigation.WorldRankingNavHost
import com.tatoeapps.athleticsrankingpoints.presentation.components.AppBottomBar
import com.tatoeapps.athleticsrankingpoints.presentation.theme.AthleticsRankingPointsTheme


@ExperimentalAnimationApi
@Composable
fun WorldRankingApp(
) {
//    AthleticsRankingPointsTheme {

      val allScreens = WorldRankingTabScreens.values().toList()
      val navController = rememberNavController()
      val backstackEntry = navController.currentBackStackEntryAsState()
      val currentScreen = WorldRankingTabScreens.fromRoute(
        backstackEntry.value?.destination?.route
      )

      Scaffold(
        backgroundColor = AthleticsRankingPointsTheme.colors.backgroundScreen,
        bottomBar = {
          AppBottomBar(
            navController = navController,
            allTabScreens = allScreens,
            onTabSelected = { screen ->
              navController.navigate(screen.name){
                popUpTo(WorldRankingTabScreens.Home.name) {inclusive = false}
              } },
            currentTabScreen = currentScreen
          )
        },
      ) { innerPadding ->
        WorldRankingNavHost(
          navHostController = navController,
          modifier = Modifier.padding(innerPadding)
        )
      }
      }
//  }