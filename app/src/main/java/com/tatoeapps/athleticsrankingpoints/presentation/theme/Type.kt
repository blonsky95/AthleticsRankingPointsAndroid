package com.tatoeapps.athleticsrankingpoints.presentation.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.tatoeapps.athleticsrankingpoints.R

private val EczarFontFamily = FontFamily(
  Font(R.font.eczar_regular),
  Font(R.font.eczar_semibold, FontWeight.SemiBold)
)
private val RobotoCondensed = FontFamily(
  Font(R.font.robotocondensed_regular),
  Font(R.font.robotocondensed_light, FontWeight.Light),
  Font(R.font.robotocondensed_bold, FontWeight.Bold)
)

private val InconsolataFontFamily = FontFamily(
  Font(R.font.inconsolata_medium),
  Font(R.font.inconsolata_bold, FontWeight.Bold)
)

private val CousineFontFamily = FontFamily(
  Font(R.font.cousine_regular),
  Font(R.font.cousine_bold, FontWeight.Bold)
)

data class AppTypography(
  val title1 :TextStyle= TextStyle(
    fontWeight = FontWeight.Light,
    fontSize = 60.sp,
    letterSpacing = (-0.5).sp,
  ),
  val title2:TextStyle = TextStyle(
    fontWeight = FontWeight.Bold,
    fontSize = 36.sp,
    fontFamily = CousineFontFamily,
    letterSpacing = 0.5.sp,
  ),
  val title3:TextStyle = TextStyle(
    fontWeight = FontWeight.Bold,
    fontSize = 24.sp,
    fontFamily = CousineFontFamily,
    letterSpacing = 0.4.sp,
  ),
  val text1:TextStyle = TextStyle(
    fontWeight = FontWeight.W700,
    fontSize = 34.sp
  ),
  val text2:TextStyle = TextStyle(
    fontWeight = FontWeight.Bold,
    fontSize = 18.sp,
    fontFamily = InconsolataFontFamily
  ),
  val text3:TextStyle = TextStyle(
    fontFamily = InconsolataFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp,
    letterSpacing = 0.2.sp,
    ),
  val text4:TextStyle = TextStyle(
    fontSize = 14.sp,
    fontFamily = CousineFontFamily,
    ),
  val text5:TextStyle = TextStyle(
    fontFamily = InconsolataFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp,
  ),
)

val TextStyle.bold: TextStyle
  get() = this.copy(fontWeight = FontWeight.Bold)

val TextStyle.grey: TextStyle
  get() = this.copy(color = darkGrey)
val TextStyle.white: TextStyle
  get() = this.copy(color = com.tatoeapps.athleticsrankingpoints.presentation.theme.white)
val TextStyle.beige: TextStyle
  get() = this.copy(color = com.tatoeapps.athleticsrankingpoints.presentation.theme.beige)
val TextStyle.navyBlue: TextStyle
  get() = this.copy(color = com.tatoeapps.athleticsrankingpoints.presentation.theme.navyBlue)
val TextStyle.textBlue: TextStyle
  get() = this.copy(color = com.tatoeapps.athleticsrankingpoints.presentation.theme.textBlue)

internal val LocalTypography = staticCompositionLocalOf { AppTypography() }


