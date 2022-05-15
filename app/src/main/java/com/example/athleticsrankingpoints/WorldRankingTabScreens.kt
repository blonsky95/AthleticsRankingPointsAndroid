package com.example.athleticsrankingpoints

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Icecream
import androidx.compose.material.icons.filled.LooksOne
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Calculate
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector

enum class WorldRankingTabScreens (
  val icon: ImageVector
  ) {

  Home(
    icon = Icons.Outlined.Home
  ),
  Calculator(
    icon = Icons.Outlined.Calculate,
  ),
//  Information(
//    icon = Icons.Filled.Info,
//  ),
  Saved(
    icon = Icons.Outlined.BookmarkBorder
  );

  companion object {
    fun fromRoute(route: String?): WorldRankingTabScreens =
      when(route?.substringBefore("/")) {
        Home.name -> Home
        Calculator.name -> Calculator
//        Information.name -> Information
        Saved.name -> Saved
        "EventGroupSimulator" -> Calculator
        null -> Home
        else -> throw IllegalArgumentException("Route $route is not recognized")
      }

  }

}