package com.example.athleticsrankingpoints.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.athleticsrankingpoints.domain.interfaces.SelectableIdentifiable
import com.example.athleticsrankingpoints.presentation.theme.AthleticsRankingPointsTheme
import com.example.athleticsrankingpoints.presentation.theme.bold


//This is a stateless composable that allows useer to select between 2 options, it displays a white border around the selected box.
@Composable
fun CustomTwoRadioButtonGroup(
  modifier: Modifier,
  selectedOption: SelectableIdentifiable,
  buttonOptions: List<SelectableIdentifiable>,
  borderColorDisabled: Color = Transparent,
  borderColorEnabled: Color = AthleticsRankingPointsTheme.colors.textBlack,
  verticalPadding: Dp = 4.dp,
  onSelectionChange: (SelectableIdentifiable) -> Unit
) {
  Row (modifier){
    buttonOptions.forEach {
      val buttonBorderColor:Color by animateColorAsState(
        targetValue = if (selectedOption==it) borderColorEnabled else borderColorDisabled,
        animationSpec =  tween(
          durationMillis = 500,
          easing = LinearOutSlowInEasing
        )
      )
      Box(Modifier
        .weight(1F)
        .selectable(
          selected = selectedOption == it,
          onClick = {
            onSelectionChange(it)
          }
        )) {
        Text(modifier = Modifier
          .border(width = 1.dp, color = buttonBorderColor)
          .background(color = if (selectedOption==it) AthleticsRankingPointsTheme.colors.backgroundComponent else Transparent)
          .padding(vertical = verticalPadding)
          .fillMaxWidth(),
          style = AthleticsRankingPointsTheme.typography.text1.bold,
          color = if (selectedOption==it) AthleticsRankingPointsTheme.colors.textBlack else AthleticsRankingPointsTheme.colors.textWhite,
          textAlign = TextAlign.Center,
          text = it.getReadableText().uppercase())
      }
    }
  }
}