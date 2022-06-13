package com.tatoeapps.athleticsrankingpoints.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.tatoeapps.athleticsrankingpoints.domain.interfaces.SelectableIdentifiable
import com.tatoeapps.athleticsrankingpoints.domain.models.AthleticsEvent

@Composable
fun CategorySelector(selectedSex: SelectableIdentifiable, selectedDoor: SelectableIdentifiable, onSexSelectionChange: (SelectableIdentifiable) -> Unit, onDoorSelectionChange: (SelectableIdentifiable) -> Unit)  {
  Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
    CustomTwoRadioButtonGroup(
      modifier = Modifier.weight(1F),
      selectedOption = selectedSex,
      buttonOptions = AthleticsEvent.listSexOptions
    ) {
      onSexSelectionChange(it)
    }
    CustomTwoRadioButtonGroup(
      modifier = Modifier.weight(1F),
      selectedOption = selectedDoor,
      buttonOptions = AthleticsEvent.listDoorOptions
    ) {
      onDoorSelectionChange(it)
    }
  }
}

