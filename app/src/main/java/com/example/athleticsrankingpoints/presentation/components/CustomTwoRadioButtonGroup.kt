package com.example.athleticsrankingpoints.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.athleticsrankingpoints.domain.interfaces.SelectableIdentifiable


//This is a stateless composable that allows useer to select between 2 options, it displays a white border around the selected box.
@Composable
fun CustomTwoRadioButtonGroup(modifier: Modifier, selectedOption: SelectableIdentifiable, buttonOptions: List<SelectableIdentifiable>, onSelectionChange: (SelectableIdentifiable) -> Unit) {
  Row (modifier){
    buttonOptions.forEach {
      var borderColor = Color.Transparent
      Box(Modifier
        .weight(1F)
        .selectable(
          selected = selectedOption == it,
          onClick = {
            onSelectionChange(it)
          }
        )) {
        if (selectedOption == it) {
          borderColor = Color.White
        }
        Text(modifier = Modifier
          .border(width = 2.dp, color = borderColor, shape = RoundedCornerShape(4.dp))
          .padding(top = 4.dp, bottom = 4.dp)
          .fillMaxWidth(),
          textAlign = TextAlign.Center,
          text = it.getReadableText())
      }
    }
  }
}