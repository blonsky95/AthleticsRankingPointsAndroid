package com.example.athleticsrankingpoints.presentation.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val normalBlue = Color(0xFF5780E3)
val lightGrey = Color(0xFFF6F8FA)
val lightBlue = Color(0xFF5D86EA)
val darkBlue = Color(0xFF456CC9)
val white = Color(0xFFFFFFFF)
val grey = Color(0xFFE4E6EB)
val lightColor6 = Color(0xFF3700B3)

val darkColor1 = Color(0xFFBB86FC)
val darkColor2 = Color(0xFF6200EE)
val darkColor3 = Color(0xFF3700B3)
val darkColor4 = Color(0xFFBB86FC)
val darkColor5 = Color(0xFF6200EE)
val darkColor6 = Color(0xFF3700B3)

fun lightColors(
  backgroundPrimary: Color = normalBlue,
  backgroundSecondary: Color = lightGrey,
  elevatedSurface: Color = darkBlue,
  color4: Color = white,
  color5: Color = grey,
  color6: Color = lightColor6,
) : AppColors = AppColors(
  backgroundPrimary,
  backgroundSecondary,
  elevatedSurface,
  color4,
  color5,
  color6,
  isLightTheme = true
)

fun darkColors(
  color1: Color = darkColor1,
  color2: Color = darkColor2,
  color3: Color = darkColor3,
  color4: Color = darkColor4,
  color5: Color = darkColor5,
  color6: Color = darkColor6,
) : AppColors = AppColors(
  color1,
  color2,
  color3,
  color4,
  color5,
  color6,
  isLightTheme = false
)

internal val LocalColors = staticCompositionLocalOf { lightColors() }

class AppColors(
  backgroundPrimary: Color,
  backgroundSecondary: Color,
  elevatedSurface: Color,
  color4: Color,
  color5: Color,
  color6: Color,
  isLightTheme: Boolean
) {
  var backgroundPrimary by mutableStateOf(backgroundPrimary)
    private set
  var backgroundSecondary by mutableStateOf(backgroundSecondary)
    private set
  var elevatedSurface by mutableStateOf(elevatedSurface)
    private set
  var color4 by mutableStateOf(color4)
    private set
  var color5 by mutableStateOf(color5)
    private set
  var color6 by mutableStateOf(color6)
    private set
  var isLightTheme by mutableStateOf(isLightTheme)
    private set


  fun copy(
    background: Color = this.backgroundPrimary,
    surface: Color = this.backgroundSecondary,
    color3: Color = this.elevatedSurface,
    color4: Color = this.color4,
    color5: Color = this.color5,
    color6: Color = this.color6,
    isLightTheme: Boolean = this.isLightTheme
  ) : AppColors = AppColors(
    background,
    surface,
    color3,
    color4,
    color5,
    color6,
    isLightTheme
  )

  fun updateColorsFrom(newAppColors: AppColors) {
    backgroundPrimary = newAppColors.backgroundPrimary
    backgroundSecondary = newAppColors.backgroundSecondary
    elevatedSurface = newAppColors.elevatedSurface
    color4 = newAppColors.color4
    color5 = newAppColors.color5
    color6 = newAppColors.color6
    isLightTheme = newAppColors.isLightTheme
  }
}