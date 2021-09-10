package com.example.athleticsrankingpoints.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomDivider() {
  Divider(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
    color = Color(0xFFACACAC), thickness = 2.dp)
}