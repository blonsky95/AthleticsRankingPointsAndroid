package com.example.athleticsrankingpoints.presentation.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val normalBlue = Color(0xFF5780E3)
val almostWhiteGrey = Color(0xFFFFFFFF)
val lightGrey = Color(0xFFF4F4F4)
val darkGrey = Color(0xFFD4D6D8)
val black = Color(0xFF000000)
val lightBlue = Color(0xFF5D86EA)
val darkBlue = Color(0xFF456CC9)
val white = Color(0xFFFFFFFF)

val lightColor6 = Color(0xFF3700B3)

val darkColor1 = Color(0xFFBB86FC)
val darkColor2 = Color(0xFF6200EE)
val darkColor3 = Color(0xFF3700B3)
val darkColor4 = Color(0xFFBB86FC)
val darkColor5 = Color(0xFF6200EE)
val darkColor6 = Color(0xFF3700B3)

fun lightColors(
  background: Color = white,
  backgroundGrey: Color = lightGrey,
  selectedGrey: Color = darkGrey,
  textBlack: Color = black,
  textWhite: Color = white,
  errorRed: Color = Color.Red,
) : AppColors = AppColors(
  background,
  backgroundGrey,
  selectedGrey,
  textBlack,
  textWhite,
  errorRed,
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
  color1: Color,
  color2: Color,
  color3: Color,
  color4: Color,
  color5: Color,
  color6: Color,
  isLightTheme: Boolean
) {
  var background by mutableStateOf(color1)
    private set
  var backgroundGrey by mutableStateOf(color2)
    private set
  var selectedGrey by mutableStateOf(color3)
    private set
  var textBlack by mutableStateOf(color4)
    private set
  var textWhite by mutableStateOf(color5)
    private set
  var errorRed by mutableStateOf(color6)
    private set
  var isLightTheme by mutableStateOf(isLightTheme)
    private set


  fun copy(
    color1: Color = this.background,
    color2: Color = this.backgroundGrey,
    color3: Color = this.selectedGrey,
    color4: Color = this.textBlack,
    color5: Color = this.textWhite,
    color6: Color = this.errorRed,
    isLightTheme: Boolean = this.isLightTheme
  ) : AppColors = AppColors(
    color1,
    color2,
    color3,
    color4,
    color5,
    color6,
    isLightTheme
  )

  fun updateColorsFrom(newAppColors: AppColors) {
    background = newAppColors.background
    backgroundGrey = newAppColors.backgroundGrey
    selectedGrey = newAppColors.selectedGrey
    textBlack = newAppColors.textBlack
    textWhite = newAppColors.textWhite
    errorRed = newAppColors.errorRed
    isLightTheme = newAppColors.isLightTheme
  }
}