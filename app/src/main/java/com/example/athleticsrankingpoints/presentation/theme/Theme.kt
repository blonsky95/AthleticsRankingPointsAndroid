package com.example.athleticsrankingpoints.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember


object AthleticsRankingPointsTheme {
  val colors: AppColors
    @Composable
    @ReadOnlyComposable
    get() = LocalColors.current

  val typography: AppTypography
    @Composable
    @ReadOnlyComposable
    get() = LocalTypography.current

  val dimensions: AppDimensions
    @Composable
    @ReadOnlyComposable
    get() = LocalDimensions.current
}


@Composable
fun AthleticsRankingPointsTheme(
  colors: AppColors = AthleticsRankingPointsTheme.colors,
  typography: AppTypography = AthleticsRankingPointsTheme.typography,
  dimensions: AppDimensions = AthleticsRankingPointsTheme.dimensions,
  content: @Composable () -> Unit
) {
  val rememberedColors = remember { colors.copy() }.apply { updateColorsFrom(colors) }
  CompositionLocalProvider(
    LocalColors provides rememberedColors,
    LocalTypography provides typography,
    LocalDimensions provides dimensions
  ) {
    content()
  }
}
