package com.example.athleticsrankingpoints.presentation.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val background = Color(0xFFBD5065)
val surface = Color(0xFFAA3A4F)
val lightColor3 = Color(0xFF3700B3)
val lightColor4 = Color(0xFFBB86FC)
val lightColor5 = Color(0xFF6200EE)
val lightColor6 = Color(0xFF3700B3)

val darkColor1 = Color(0xFFBB86FC)
val darkColor2 = Color(0xFF6200EE)
val darkColor3 = Color(0xFF3700B3)
val darkColor4 = Color(0xFFBB86FC)
val darkColor5 = Color(0xFF6200EE)
val darkColor6 = Color(0xFF3700B3)

fun lightColors(
  background: Color = com.example.athleticsrankingpoints.presentation.theme.background,
  surface: Color = com.example.athleticsrankingpoints.presentation.theme.surface,
  color3: Color = lightColor3,
  color4: Color = lightColor4,
  color5: Color = lightColor5,
  color6: Color = lightColor6,
) : AppColors = AppColors(
  background,
  surface,
  color3,
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
  background: Color,
  surface: Color,
  color3: Color,
  color4: Color,
  color5: Color,
  color6: Color,
  isLightTheme: Boolean
) {
  var background by mutableStateOf(background)
    private set
  var surface by mutableStateOf(surface)
    private set
  var color3 by mutableStateOf(color3)
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
    background: Color = this.background,
    surface: Color = this.surface,
    color3: Color = this.color3,
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
    background = newAppColors.background
    surface = newAppColors.surface
    color3 = newAppColors.color3
    color4 = newAppColors.color4
    color5 = newAppColors.color5
    color6 = newAppColors.color6
    isLightTheme = newAppColors.isLightTheme
  }
}