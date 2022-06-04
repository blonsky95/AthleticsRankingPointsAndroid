package com.tatoeapps.athleticsrankingpoints.presentation.components

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tatoeapps.athleticsrankingpoints.R
import com.tatoeapps.athleticsrankingpoints.domain.models.CompetitionCategory
import com.tatoeapps.athleticsrankingpoints.domain.models.CompetitionCategoryGroup
import com.tatoeapps.athleticsrankingpoints.presentation.theme.AthleticsRankingPointsTheme
import com.tatoeapps.athleticsrankingpoints.presentation.theme.beige
import com.tatoeapps.athleticsrankingpoints.presentation.theme.lilac

data class PerformancePlacementDetails(
  val competitionCategoryGroup: CompetitionCategoryGroup,
  var totalPoints: String? = null,
  val category: CompetitionCategory? = null,
  val position: Int? = null,
)

@Composable
fun PlacementWithPoints2(
  performancePlacementDetails: PerformancePlacementDetails,
  onPlacementsPointsChange: (PerformancePlacementDetails) -> Unit,
) {

  var categoryExpanded by remember { mutableStateOf(false) }
  var positionsExpanded by remember { mutableStateOf(false) }

  var selectedCategory by remember {
    mutableStateOf(performancePlacementDetails.category ?: performancePlacementDetails.competitionCategoryGroup.sCategories.firstOrNull()?.let { CompetitionCategory.valueOf(it.sName) } ?: CompetitionCategory.UNKNOWN)
  }

  var selectedPosition by remember {
    mutableStateOf(performancePlacementDetails.position ?: 1)
  }

  var positionsArray by remember {
    mutableStateOf(performancePlacementDetails.competitionCategoryGroup.getPositionsForCategory(performancePlacementDetails.category ?: CompetitionCategory.UNKNOWN))
  }

  var placementPoints by remember {
    mutableStateOf(performancePlacementDetails.totalPoints ?: 0)
  }

  MyCustomTwoComposableRow {
    Row() {
      PlacementCompetitionCategoryDropDownList(
        expanded = categoryExpanded,
        categories = performancePlacementDetails.competitionCategoryGroup.sCategories.map { CompetitionCategory.valueOf(it.sName) },
        selectedCategory = selectedCategory,
        dropdownDrawable = if (categoryExpanded) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down,
        onDisplayClicked = { categoryExpanded = !categoryExpanded },
        onSelectionChange = {
          selectedCategory = it
          positionsArray = performancePlacementDetails.competitionCategoryGroup.getPositionsForCategory(it)
          onPlacementsPointsChange(performancePlacementDetails.copy(
            totalPoints = placementPoints.toString(),
            category = selectedCategory,
            position = selectedPosition
          ))
        },
        onDismissRequest = { categoryExpanded = false }
      )

      Spacer(modifier = Modifier.width(4.dp))

      PlacementPositionDropDownList(
        expanded = positionsExpanded,
        positions = positionsArray,
        selectedPosition = selectedPosition,
        dropdownDrawable = if (positionsExpanded) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down,
        onDisplayClicked = { positionsExpanded = !positionsExpanded },
        onSelectionChange = {
          selectedPosition = it
          placementPoints = performancePlacementDetails.competitionCategoryGroup.getPointsForPosition(it, selectedCategory)
          onPlacementsPointsChange(performancePlacementDetails.copy(
            totalPoints = placementPoints.toString(),
            category = selectedCategory,
            position = selectedPosition
          ))
        },
        onDismissRequest = { positionsExpanded = false }
      )

      Spacer(modifier = Modifier.width(4.dp))


      CustomInputField(
        customInputColors = CustomInputColors(),
        value = placementPoints.toString(),
        canFillMaxWidth = true,
        hint = "0",
        onValueChange ={
          try {
            placementPoints = it.toInt()
          } catch (e: Error) {
            Log.d("Error", "parsed user text to int threw error - user placement points: $e")
          }
          onPlacementsPointsChange(performancePlacementDetails.copy(
            totalPoints = placementPoints.toString(),
            category = selectedCategory,
            position = selectedPosition
          ))
        }
      )
    }

//    MyCustomTextField(performance = placementPoints, hint = "Placement (0)", onPerformanceChange = onPlacementsPointsChange)
    MyCustomText(text = placementPoints.toString())
  }
}


@Composable
fun PlacementCompetitionCategoryDropDownList(
  modifier: Modifier = Modifier,
  expanded: Boolean,
  categories: List<CompetitionCategory>,
  selectedCategory: CompetitionCategory,
  @DrawableRes
  dropdownDrawable: Int = R.drawable.ic_arrow_down,
  onDisplayClicked: (Boolean) -> Unit,
  onSelectionChange: (CompetitionCategory) -> Unit,
  onDismissRequest: () -> Unit,
) {
  Column(Modifier.height(IntrinsicSize.Max)) {
    Row(
      modifier = modifier
        .padding(vertical = 4.dp)
        .widthIn(min = 48.dp)
        .background(color = lilac.copy(alpha = 0.5f))
        .clickable {
          onDisplayClicked(expanded)
        },
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Text(
        modifier = Modifier.padding(4.dp),
        text = selectedCategory.name,
        color = AthleticsRankingPointsTheme.colors.textBlack,
        style = TextStyle(fontSize = 13.sp)
      )
      Icon(
        modifier = Modifier
          .size(28.dp)
          .padding(6.dp),
        painter = painterResource(id = dropdownDrawable),
        tint = AthleticsRankingPointsTheme.colors.textBlack,
        contentDescription = ""
      )
    }
    DropdownMenu(
      modifier = Modifier.background(AthleticsRankingPointsTheme.colors.backgroundScreen),
      expanded = expanded,
      onDismissRequest = { onDismissRequest() }
    ) {
      categories.forEach { category ->
        DropdownMenuItem(onClick = {
          onSelectionChange(category)
          onDisplayClicked(false)
        }) {
          Column {
            DropdownItem(category.categoryName)
          }
        }
      }
    }
  }
}



@Composable
fun PlacementPositionDropDownList(
  modifier: Modifier = Modifier,
  expanded: Boolean,
  positions: List<Int>,
  selectedPosition: Int,
  @DrawableRes
  dropdownDrawable: Int = R.drawable.ic_arrow_down,
  onDisplayClicked: (Boolean) -> Unit,
  onSelectionChange: (Int) -> Unit,
  onDismissRequest: () -> Unit,
) {
  Column(Modifier.height(IntrinsicSize.Max)) {
    Row(
      modifier = modifier
        .padding(vertical = 4.dp)
        .widthIn(min = 48.dp)
        .background(color = lilac.copy(alpha = 0.5f))
        .clickable {
          onDisplayClicked(expanded)
        },
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Text(
        modifier = Modifier.padding(4.dp),
        text = selectedPosition.toString(),
        color = AthleticsRankingPointsTheme.colors.textBlack,
        style = TextStyle(fontSize = 13.sp)
      )
      Icon(
        modifier = Modifier
          .size(28.dp)
          .padding(6.dp),
        painter = painterResource(id = dropdownDrawable),
        tint = AthleticsRankingPointsTheme.colors.textBlack,
        contentDescription = ""
      )
    }
    DropdownMenu(
      modifier = Modifier.background(AthleticsRankingPointsTheme.colors.backgroundScreen),
      expanded = expanded,
      onDismissRequest = { onDismissRequest() }
    ) {
      positions.forEach { position ->
        DropdownMenuItem(onClick = {
          onSelectionChange(position)
          onDisplayClicked(false)
        }) {
          Column {
            DropdownItem(text = position.toString())
          }
        }
      }
    }
  }
}



