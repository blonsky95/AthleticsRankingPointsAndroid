package com.tatoeapps.athleticsrankingpoints.domain.models

import org.junit.Test

class AthleticsEventTest {

  private lateinit var testAthleticsEvent: AthleticsEvent

  @Test
  fun `wrong input returns 0`() {
    testAthleticsEvent = AthleticsEvent.getSampleEvent()

    val actual = testAthleticsEvent.getPointsString("asdsad")

    val expected = "0"

    assert(actual==expected)
  }

  @Test
  fun `right input returns points`() {
    testAthleticsEvent = AthleticsEvent.getSampleEvent()

    val actual = testAthleticsEvent.getPointsString("10.55")

    val expected = "1024"

    assert(actual==expected)
  }

  @Test
  fun `right input over 2000p returns error string`() {
    testAthleticsEvent = AthleticsEvent.getSampleEvent()

    val actual = testAthleticsEvent.getPointsString("1")

    val expected = "Limit set to 2000p"

    assert(actual==expected)
  }
}