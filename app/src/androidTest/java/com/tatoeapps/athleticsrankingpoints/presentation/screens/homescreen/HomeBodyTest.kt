package com.tatoeapps.athleticsrankingpoints.presentation.screens.homescreen

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import arrow.core.valid
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeBodyTest {

  @get: Rule
  val composeTestRule = createComposeRule()   // compose rule is required to get access to the composable component

  @Before
  fun setUp() {
    composeTestRule.setContent {    // setting our composable as content for test
        PointLookUpBody()
    }
    composeTestRule.waitUntil (timeoutMillis = 5000) {
      composeTestRule
        .onNodeWithTag("eventListDisplayer")
        .fetchSemanticsNode().children.isNotEmpty()
    }
  }

  @Test
  fun changeEventInList_updatesSelectedEventDisplayText() {
    composeTestRule.onNodeWithTag("listEvent_200m", useUnmergedTree = true).assertExists().performClick()
    composeTestRule.onNodeWithTag("selectedEventTextDisplay", useUnmergedTree = true).assertTextEquals("200m")
  }

  @Test
  fun tooManyPointsInput_showsError() {
    composeTestRule.onNodeWithTag("performanceInput", useUnmergedTree = true).performTextInput("1")
    composeTestRule.onNodeWithTag("homePointsDisplay", useUnmergedTree = true).assertTextEquals("Limit set to 2000p")
  }

  @Test
  fun nonValidPointsInput_showsError() {
    composeTestRule.onNodeWithTag("performanceInput", useUnmergedTree = true).performTextInput(",")
    composeTestRule.onNodeWithTag("homePointsDisplay", useUnmergedTree = true).assertTextEquals("Error")
  }


}