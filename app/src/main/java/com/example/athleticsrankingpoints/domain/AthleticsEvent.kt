package com.example.athleticsrankingpoints.domain

import kotlin.math.floor
import kotlin.math.pow


data class AthleticsEvent (
  val sName:String,
  val sType:String, //run, jump or throw
  val sCategory: String,
  val sKey: String,
  val sCoefficients:HashMap<String, Double>
){

  companion object{
    const val type_run="type_run" //only needs seconds and hundredths
    const val type_long_run="type_long_run" //needs minutes, seconds and hundredths
    const val type_very_long_run="type_very_long_run" //needs hours, minutes, seconds and hundredths
    const val type_jump="type_jump" //only needs metres
    const val type_throw="type_throw" //only needs metres
    const val type_combined_events="type_combined_events" //only needs points aka Int, no decimals

    const val categoryIndoorFemale = "category_indoor_female"
    const val categoryIndoorMale = "category_indoor_male"
    const val categoryOutdoorFemale = "category_outdoor_female"
    const val categoryOutdoorMale = "category_outdoor_male"

    const val sexFemale = "Female"
    const val sexMale = "Male"
    const val doorIndoor = "Indoor"
    const val doorOutdoor = "Outdoor"

    val listSexOptions = listOf(sexMale, sexFemale)
    val listDoorOptions = listOf(doorIndoor, doorOutdoor)

    fun getSampleEvent():AthleticsEvent{
      return AthleticsEvent(
        sName="50m",
        sType = type_run,
        sCategory = categoryIndoorMale,
        sKey = "50_m_i",
        sCoefficients = hashMapOf("a" to 95.8223538630, "b" to -9.1994010874, "c" to -0.3765978316)
      )
    }

    fun getThreeSampleEvents():List<AthleticsEvent>{
      return listOf(AthleticsEvent(
        sName="100m",
        sType = type_run,
        sCategory = categoryOutdoorMale,
        sKey = "100_m_o",
        sCoefficients = hashMapOf("a" to 24.6422116633, "b" to -16.9975315583, "c" to -0.2186620480)
      ),
        AthleticsEvent(
          sName="Decathlon",
          sType = type_combined_events,
          sCategory = categoryOutdoorMale,
          sKey = "decathlon_m_o",
          sCoefficients = hashMapOf("a" to 0.0000009772, "b" to 71186.6785041732, "c" to -5001.0472144005)
        ),
        AthleticsEvent(
          sName="Shot put",
          sType = type_throw,
          sCategory = categoryOutdoorMale,
          sKey = "shot_put_m_o",
          sCoefficients = hashMapOf("a" to 0.0423461436, "b" to 684.8281542324, "c" to -19915.7245727669)
        ))
    }

    fun getLongerEventsList():List<AthleticsEvent> {
      val list = mutableListOf<AthleticsEvent>()
      repeat(30) {
        list.add(getThreeSampleEvents().random())
      }
      return list
    }
  }

  fun getPointsString(performance:String) : String {
    if (performance.toDoubleOrNull() == null || floor(performance.toDouble()) == 0.0) {
      return "0"
    } else {
      val performanceDouble =
        if (this.sType== type_combined_events) {
          floor(performance.toDouble())
        } else {
          performance.toDouble()
        }
      val pointsInt = getPoints(performanceDouble)
      if (pointsInt>2000) {
        return "Limit set to 2000p"
      }
      if (pointsInt<0) {
        return "0"
      }
      return getPoints(performanceDouble).toString()
    }
  }

  private fun getPoints(performance: Double): Int {
    return floor(sCoefficients["a"]!! * (performance + sCoefficients["b"]!!).pow(2) + sCoefficients["c"]!!).toInt()
  }

  //Use this function if you want the event name to display "(i)" for indoor.
  fun getDoorInclusiveName():String {
    var string = sName
    if (sCategory== categoryIndoorMale || sCategory== categoryIndoorFemale) {
      string+=" (i)"
    }
    return string
  }

}