package com.example.athleticsrankingpoints.presentation.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.athleticsrankingpoints.R

private val EczarFontFamily = FontFamily(
  Font(R.font.eczar_regular),
  Font(R.font.eczar_semibold, FontWeight.SemiBold)
)
private val RobotoCondensed = FontFamily(
  Font(R.font.robotocondensed_regular),
  Font(R.font.robotocondensed_light, FontWeight.Light),
  Font(R.font.robotocondensed_bold, FontWeight.Bold)
)

data class AppTypography(
  val title1 :TextStyle= TextStyle(
    fontWeight = FontWeight.Light,
    fontSize = 60.sp,
    letterSpacing = (-0.5).sp,
  ),
  val title2:TextStyle = TextStyle(
    fontWeight = FontWeight.SemiBold,
    fontSize = 30.sp,
    fontFamily = RobotoCondensed,
    letterSpacing = 1.5.sp,
  ),
  val text1:TextStyle = TextStyle(
    fontWeight = FontWeight.W400,
    fontSize = 14.sp
  ),
  val text2:TextStyle = TextStyle(
    fontWeight = FontWeight.W700,
    fontSize = 34.sp
  )
)

internal val LocalTypography = staticCompositionLocalOf { AppTypography() }


