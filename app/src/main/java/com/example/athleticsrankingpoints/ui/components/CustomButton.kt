package com.example.athleticsrankingpoints.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomButton(text: String, onButtonTap: () -> Unit) {
  OutlinedButton(
    //Ripple not working or not visible
    modifier = Modifier.clickable(
      onClick = {},
      interactionSource = remember { MutableInteractionSource() },
      indication = rememberRipple(radius = 30.dp)
    ),
    onClick = onButtonTap,
    colors = ButtonDefaults.outlinedButtonColors(
      backgroundColor = Color.White,
      contentColor = Color.DarkGray,
      disabledContentColor = Color(0xFF666699)
    ),
    enabled = true
  ) {
    Text(
      text = text,
      style = MaterialTheme.typography.button)
  }
}