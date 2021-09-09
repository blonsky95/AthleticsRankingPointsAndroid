package com.example.athleticsrankingpoints

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LooksOne
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

enum class WorldRankingScreen (
  val icon: ImageVector
  ) {

  PointLookUp(
    icon = Icons.Filled.Search
  ),
  Simulator(
    icon = Icons.Filled.LooksOne,
  ),
  Information(
    icon = Icons.Filled.Info,
  );

  companion object {
    fun fromRoute(route: String?): WorldRankingScreen =
      when(route?.substringBefore("/")) {
        PointLookUp.name -> PointLookUp
        Simulator.name -> Simulator
        Information.name -> Information
        "EventGroupSimulator" -> Simulator
        null -> PointLookUp
        else -> throw IllegalArgumentException("Route $route is not recognized")
      }

  }

}