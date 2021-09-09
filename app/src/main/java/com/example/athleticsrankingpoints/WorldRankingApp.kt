package com.example.athleticsrankingpoints

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.athleticsrankingpoints.navigation.WorldRankingNavHost
import com.example.athleticsrankingpoints.ui.components.TopTabRow
import com.example.athleticsrankingpoints.ui.theme.AthleticsRankingPointsTheme


@Composable
fun WorldRankingApp(
) {
    AthleticsRankingPointsTheme {

      val allScreens = WorldRankingScreen.values().toList()
      val navController = rememberNavController()
      val backstackEntry = navController.currentBackStackEntryAsState()
      val currentScreen = WorldRankingScreen.fromRoute(
        backstackEntry.value?.destination?.route
      )

      Scaffold(
//        bottomBar = {
//          TopTabRow(
//            allScreens = allScreens,
//            onTabSelected = { screen ->
//              navController.navigate(screen.name) },
//            currentScreen = currentScreen
//          )
//        },
        topBar = {
          TopTabRow(
            allScreens = allScreens,
            onTabSelected = { screen ->
              navController.navigate(screen.name) },
            currentScreen = currentScreen
          )
        }

      ) { innerPadding ->
        WorldRankingNavHost(
          navHostController = navController,
          modifier = Modifier.padding(innerPadding)
        )
      }
      }
  }