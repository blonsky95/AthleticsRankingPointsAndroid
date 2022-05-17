package com.tatoeapps.athleticsrankingpoints.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.tatoeapps.athleticsrankingpoints.WorldRankingTabScreens
import com.tatoeapps.athleticsrankingpoints.presentation.theme.AthleticsRankingPointsTheme
import com.tatoeapps.athleticsrankingpoints.presentation.theme.white

@Composable
fun AppBottomBar(
  navController: NavController,
  allTabScreens: List<WorldRankingTabScreens>,
  onTabSelected: (WorldRankingTabScreens) -> Unit,
  currentTabScreen: WorldRankingTabScreens,
) {

  val navBackStackEntry by navController.currentBackStackEntryAsState()
  val currentRoute = navBackStackEntry?.destination?.route

  val sections = remember { WorldRankingTabScreens.values() }
  val routes = remember { sections.map { it.name } }

  if (currentRoute in routes) {

    Surface(
      Modifier
        .height(TabHeight)
        .fillMaxWidth(),
      color = AthleticsRankingPointsTheme.colors.selectedComponent
    ) {
      Column {
        Divider(
          modifier = Modifier.height(2.dp),
          color = AthleticsRankingPointsTheme.colors.backgroundComponent
        )
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .selectableGroup()
        ) {
          allTabScreens.forEach { screen ->
            CustomTab(
              modifier = Modifier.weight(1f),
              text = screen.getScreenName(LocalContext.current),
              icon = screen.icon,
              onSelected = { onTabSelected(screen) },
              selected = currentTabScreen == screen
            )
          }
        }
      }
    }
  }
}


@Composable
fun CustomTab(
  modifier: Modifier,
  text: String,
  icon: ImageVector,
  onSelected: () -> Unit,
  selected: Boolean,
) {
  val durationMillis = if (selected) TabFadeInAnimationDuration else TabFadeOutAnimationDuration
  val animSpec = remember {
    tween<Color>(
      durationMillis = durationMillis,
      easing = LinearEasing,
      delayMillis = TabFadeInAnimationDelay
    )
  }
  val tabTintColor by animateColorAsState(
    targetValue = if (selected) AthleticsRankingPointsTheme.colors.selectedComponent else AthleticsRankingPointsTheme.colors.textBlack.copy(alpha = InactiveTabOpacity),
    animationSpec = animSpec
  )

  val tabBackgroundColor by animateColorAsState(
    targetValue = if (selected) AthleticsRankingPointsTheme.colors.backgroundScreen else AthleticsRankingPointsTheme.colors.selectedComponent,
    animationSpec = animSpec
  )

  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
    modifier = modifier
      .selectable(
        selected = selected,
        onClick = onSelected,
        role = Role.Tab,
        interactionSource = remember {
          MutableInteractionSource()
        },
        indication = rememberRipple(
          bounded = true,
          radius = Dp.Unspecified,
          color = Color.Unspecified
        )
      )
      .fillMaxHeight()
      .background(color = tabBackgroundColor)
      .animateContentSize()
      .clearAndSetSemantics { contentDescription = text }
  ) {
    Icon(imageVector = icon, contentDescription = text, tint = tabTintColor)
    if (selected) {
      Text(text = text.uppercase(), style = AthleticsRankingPointsTheme.typography.text4.white)
    }
  }

}

private val TabHeight = 64.dp
private const val InactiveTabOpacity = 0.60f

private const val TabFadeInAnimationDuration = 150
private const val TabFadeInAnimationDelay = 100
private const val TabFadeOutAnimationDuration = 100


