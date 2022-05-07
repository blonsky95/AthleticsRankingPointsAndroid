package com.example.athleticsrankingpoints.presentation.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.athleticsrankingpoints.Strings
import com.example.athleticsrankingpoints.presentation.theme.AthleticsRankingPointsTheme

@Composable
fun DialogWindow(hideDialog: () -> Unit, dialogText: String = "Missing text", onConfirmDialog: () -> Unit) {
  AlertDialog(
    backgroundColor = AthleticsRankingPointsTheme.colors.backgroundComponent,
    onDismissRequest = {
      hideDialog()
    },
    title = {
      Text(
        text = Strings.AttentionText,
        style = AthleticsRankingPointsTheme.typography.text3
      )
    },
    text = {
      Text(
        text = dialogText,
        style = AthleticsRankingPointsTheme.typography.text5
      )
    },
    confirmButton = {
      CustomButton(text = Strings.YesText, backgroundColor = AthleticsRankingPointsTheme.colors.backgroundScreen) {
        onConfirmDialog()
        hideDialog()
      }
    },
    dismissButton = {
      CustomButton(text = Strings.CancelText, backgroundColor = AthleticsRankingPointsTheme.colors.backgroundScreen, borderColor = Color.Red) {
        hideDialog()
      }
    },
  )
}

@Composable
fun AlertWindow(hideDialog: () -> Unit, dialogText: String = "Missing text") {
  AlertDialog(
    onDismissRequest = {
      hideDialog()
    },
    title = {
      Text(
        text = Strings.AttentionText,
        style = AthleticsRankingPointsTheme.typography.text3
      )
    },
    text = {
      Text(
        text = dialogText,
        style = AthleticsRankingPointsTheme.typography.text5
      )
    },
    confirmButton = {
      CustomButton(text = Strings.YesText, backgroundColor = AthleticsRankingPointsTheme.colors.backgroundScreen) {
        hideDialog()
      }
    }
  )
}


@Preview
@Composable
fun PreviewDialogWindow() {
  DialogWindow(hideDialog = {}, dialogText = "whadup this is the text", onConfirmDialog = {})
}
