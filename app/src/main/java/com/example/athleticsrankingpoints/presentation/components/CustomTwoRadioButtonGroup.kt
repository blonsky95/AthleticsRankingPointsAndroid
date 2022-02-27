package com.example.athleticsrankingpoints.presentation.components

import androidx.compose.animation.Animatable
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.athleticsrankingpoints.domain.interfaces.SelectableIdentifiable
import com.example.athleticsrankingpoints.presentation.theme.AthleticsRankingPointsTheme


//This is a stateless composable that allows useer to select between 2 options, it displays a white border around the selected box.
@Composable
fun CustomTwoRadioButtonGroup(modifier: Modifier, selectedOption: SelectableIdentifiable, buttonOptions: List<SelectableIdentifiable>, onSelectionChange: (SelectableIdentifiable) -> Unit) {
  Row (modifier){
    buttonOptions.forEach {

//      val initialColor = Color(0xFF302522)
//      val targetColor = Color(0xFFede0dc)
//      val animateColor = remember { Animatable(initialColor) }
//      LaunchedEffect(animateColor) {
//        animateColor.animateTo(
//          targetValue = targetColor,
//          animationSpec = repeatable(
//            animation = tween(
//              durationMillis = 2000,
//              easing = LinearEasing,
//              delayMillis = 500
//            ),
//            repeatMode = RepeatMode.Restart,
//            iterations = 3
//          )
//        )
//      }

      val borderColor2:Color by animateColorAsState(
        targetValue = if (selectedOption==it) AthleticsRankingPointsTheme.colors.backgroundPrimary else Transparent,
        animationSpec =  tween(
          durationMillis = 500,
          easing = LinearOutSlowInEasing
        )
      )
//      var borderColor = Transparent
      Box(Modifier
        .weight(1F)
        .selectable(
          selected = selectedOption == it,
          onClick = {
            onSelectionChange(it)
          }
        )) {
//        if (selectedOption == it) {
//          borderColor = AthleticsRankingPointsTheme.colors.backgroundPrimary
//        }
        Text(modifier = Modifier
          .border(width = 2.dp, color = borderColor2, shape = RoundedCornerShape(4.dp))
          .padding(top = 4.dp, bottom = 4.dp)
          .fillMaxWidth(),
          textAlign = TextAlign.Center,
          text = it.getReadableText())
      }
    }
  }
}