package com.tatoeapps.athleticsrankingpoints

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Calculate
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector

enum class WorldRankingTabScreens (
  val icon: ImageVector,
  val nameResInt: Int
  ) {

  Home(
    icon = Icons.Outlined.Home,
    nameResInt = R.string.screen_home
  ),
  Calculator(
    icon = Icons.Outlined.Calculate,
    nameResInt = R.string.screen_calculator
  ),
//  Information(
//    icon = Icons.Filled.Info,
//  ),
  Saved(
    icon = Icons.Outlined.BookmarkBorder,
  nameResInt = R.string.screen_saved
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

  fun getScreenName(context: Context) = context.getResString(this.nameResInt)

}