package com.example.athleticsrankingpoints.presentation.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val normalBlue = Color(0xFF5780E3)
val almostWhiteGrey = Color(0xFFFFFFFF)
val lightGrey = Color(0xCCF4F4F4)
val darkGrey = Color(0xFFD4D6D8)
val black = Color(0xFF000000)
val lightBlue = Color(0xFF5D86EA)
val textBlue = Color(0xFF7650D4)
val white = Color(0xFFFFFFFF)
val navyBlue = Color(0xFF25273C)
val beige = Color(0xFFFFDCBE)
val lilac = Color(0xFFC492EC)

val lightColor6 = Color(0xFF3700B3)

val darkColor1 = Color(0xFFBB86FC)
val darkColor2 = Color(0xFF6200EE)
val darkColor3 = Color(0xFF3700B3)
val darkColor4 = Color(0xFFBB86FC)
val darkColor5 = Color(0xFF6200EE)
val darkColor6 = Color(0xFF3700B3)


fun darkColors(
  backgroundScreen: Color = navyBlue,
  backgroundComponent: Color = beige,
  selectedComponent: Color = white,
  textBlack: Color = black,
  textWhite: Color = white,
  textTheme: Color = beige,
  errorRed: Color = Color.Red,
  buttonBorder: Color = lilac
) : AppColors = AppColors(
  backgroundScreen,
  backgroundComponent,
  selectedComponent,
  textBlack,
  textWhite,
  textTheme,
  errorRed,
  buttonBorder,
  isLightTheme = false
)

fun lightColors(
  backgroundScreen: Color = white,
  backgroundComponent: Color = lightGrey,
  selectedComponent: Color = darkGrey,
  textBlack: Color = black,
  textWhite: Color = white,
  textTheme: Color = white,
  errorRed: Color = Color.Red,
  buttonBorder: Color = lilac
) : AppColors = AppColors(
  backgroundScreen,
  backgroundComponent,
  selectedComponent,
  textBlack,
  textWhite,
  textTheme,
  errorRed,
  buttonBorder,
  isLightTheme = true
)

internal val LocalColors = staticCompositionLocalOf { darkColors() }

class AppColors(
  color1: Color,
  color2: Color,
  color3: Color,
  color4: Color,
  color5: Color,
  color6: Color,
  color7: Color,
  color8: Color,
  isLightTheme: Boolean
) {
  var backgroundScreen by mutableStateOf(color1)
    private set
  var backgroundComponent by mutableStateOf(color2)
    private set
  var selectedComponent by mutableStateOf(color3)
    private set
  var textBlack by mutableStateOf(color4)
    private set
  var textWhite by mutableStateOf(color5)
    private set
  var textTheme by mutableStateOf(color7)
    private set
  var errorRed by mutableStateOf(color6)
    private set
  var buttonBorder by mutableStateOf(color8)
    private set
  var isLightTheme by mutableStateOf(isLightTheme)
    private set


  fun copy(
    color1: Color = this.backgroundScreen,
    color2: Color = this.backgroundComponent,
    color3: Color = this.selectedComponent,
    color4: Color = this.textBlack,
    color5: Color = this.textWhite,
    color6: Color = this.errorRed,
    color7: Color = this.textTheme,
    color8: Color = this.buttonBorder,
    isLightTheme: Boolean = this.isLightTheme
  ) : AppColors = AppColors(
    color1,
    color2,
    color3,
    color4,
    color5,
    color6,
    color7,
    color8,
    isLightTheme
  )

  fun updateColorsFrom(newAppColors: AppColors) {
    backgroundScreen = newAppColors.backgroundScreen
    backgroundComponent = newAppColors.backgroundComponent
    selectedComponent = newAppColors.selectedComponent
    textBlack = newAppColors.textBlack
    textWhite = newAppColors.textWhite
    textTheme = newAppColors.textTheme
    errorRed = newAppColors.errorRed
    buttonBorder = newAppColors.buttonBorder
    isLightTheme = newAppColors.isLightTheme
  }
}