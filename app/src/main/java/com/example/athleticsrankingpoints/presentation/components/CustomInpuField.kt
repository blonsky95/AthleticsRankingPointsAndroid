package com.example.athleticsrankingpoints.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.athleticsrankingpoints.presentation.theme.AthleticsRankingPointsTheme

@Composable
fun CustomInputField(customInputColors: CustomInputColors, isUnitValueValid: Boolean = true, value: String, setMaxWidth:Boolean = false, keyboardType: KeyboardType = KeyboardType.Text, hint: String = "hint", onValueChange: (String) -> Unit) {
  BasicTextField(
    modifier = Modifier
      .widthIn(min = 64.dp, max = if (setMaxWidth) Dp.Unspecified else 96.dp)
      .width(IntrinsicSize.Min)
      .fillMaxHeight()
      .background(customInputColors.valueTextBackground)
      .border(width = 1.dp, color = if (isUnitValueValid) customInputColors.borderColor else Color.Red)
      .padding(start = 4.dp, end = 8.dp)
      .padding(vertical = 4.dp),
    value = value,
    onValueChange = onValueChange,
    textStyle = AthleticsRankingPointsTheme.typography.text3.copy(color = customInputColors.valueText),
    keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
    cursorBrush = SolidColor(customInputColors.brushColor),
    decorationBox = { innerTextField ->
      if (value.isEmpty()) {
        Text(
          text = hint,
          style = AthleticsRankingPointsTheme.typography.text3,
          color = customInputColors.valueText.copy(alpha = 0.4f)
        )
      }
      innerTextField()
    },
  )
}