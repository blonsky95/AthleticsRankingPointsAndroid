package com.example.athleticsrankingpoints.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.athleticsrankingpoints.presentation.theme.AthleticsRankingPointsTheme

@Composable
fun CustomButton(modifier:Modifier = Modifier, text: String, borderColor:Color? = null, backgroundColor:Color? = null, onButtonTap: () -> Unit) {
  OutlinedButton(
    //Ripple not working or not visible
    modifier = Modifier.clickable(
      onClick = {},
      interactionSource = remember { MutableInteractionSource() },
      indication = rememberRipple(radius = 30.dp)
    ),
    border = BorderStroke(
      width = 2.dp, borderColor ?: AthleticsRankingPointsTheme.colors.buttonBorder
    ),
    onClick = onButtonTap,
    colors = ButtonDefaults.outlinedButtonColors(
      backgroundColor = backgroundColor ?: Color.Transparent,
      contentColor = AthleticsRankingPointsTheme.colors.textWhite,
      disabledContentColor = Color(0xFF666699)
    ),
    enabled = true
  ) {
    Text(
      text = text.uppercase(),
      style = AthleticsRankingPointsTheme.typography.text3
    )
  }
}