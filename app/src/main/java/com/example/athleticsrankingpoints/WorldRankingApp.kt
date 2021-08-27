package com.example.athleticsrankingpoints

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.athleticsrankingpoints.navigation.WorldRankingNavHost
import com.example.athleticsrankingpoints.ui.components.TopTabRow
import com.example.athleticsrankingpoints.ui.theme.AthleticsRankingPointsTheme
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets


@Composable
fun WorldRankingApp() {
//  ProvideWindowInsets {
    AthleticsRankingPointsTheme {

      val allScreens = WorldRankingScreen.values().toList()
      val navController = rememberNavController()
      val backstackEntry = navController.currentBackStackEntryAsState()
      val currentScreen = WorldRankingScreen.fromRoute(
        backstackEntry.value?.destination?.route
      )


      Scaffold(
        bottomBar = {
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
//    }
  }