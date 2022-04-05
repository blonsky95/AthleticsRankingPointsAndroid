package com.example.athleticsrankingpoints.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.athleticsrankingpoints.data.database.ATHLETICS_EVENTS_TABLE_NAME
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.pow

@Entity(tableName = ATHLETICS_EVENTS_TABLE_NAME)
data class AthleticsEvent (
  val sName:String,
  val sType:AthleticsEventType, //run, jump or throw
  val sCategory: AthleticsEventCategory,
  @PrimaryKey
  val sKey: String,
  val sCoefficients:HashMap<String, Double>
){

  fun hasWind():Boolean {
    return (
        (sCategory== AthleticsEventCategory.category_outdoor_male || sCategory== AthleticsEventCategory.category_outdoor_female) &&
        (sName == "100m" || sName == "200m" || sName == "110mh" || sName == "100mh" || sName == "Long jump" || sName == "Triple jump")
        )
  }

  fun getPointsString(performance:String) : String {
    if (performance.toDoubleOrNull() == null || floor(performance.toDouble()) == 0.0) {
      return "0"
    } else {
      val performanceDouble =
        if (this.sType== AthleticsEventType.type_combined_events) {
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

  private fun getPoints(performance: Double)=
    performance.takeIf { performance <= abs(sCoefficients["b"]!!) }?.let {
      floor(sCoefficients["a"]!! * (it + sCoefficients["b"]!!).pow(2) + sCoefficients["c"]!!).toInt()
    }?:-1

  //Use this function if you want the event name to display "(i)" for indoor.
  fun getDoorInclusiveName():String {
    var string = sName
    if (sCategory.isIndoor()) {
      string+=" (i)"
    }
    return string
  }

  companion object{

    val listSexOptions = listOf(AthleticsSex.Male, AthleticsSex.Female)
    val listDoorOptions = listOf(AthleticsDoor.Indoor, AthleticsDoor.Outdoor)

    fun getSampleEvent(): AthleticsEvent {
      return AthleticsEvent(
        sName="100m",
        sType = AthleticsEventType.type_run,
        sCategory = AthleticsEventCategory.category_outdoor_male,
        sKey = "100_m_o",
        sCoefficients = hashMapOf("a" to 24.6422116633, "b" to -16.9975315583, "c" to -0.2186620480)
      )
    }

    fun getThreeSampleEvents():List<AthleticsEvent>{
      return listOf(AthleticsEvent(
        sName="100m",
        sType = AthleticsEventType.type_run,
        sCategory = AthleticsEventCategory.category_outdoor_male,
        sKey = "100_m_o",
        sCoefficients = hashMapOf("a" to 24.6422116633, "b" to -16.9975315583, "c" to -0.2186620480)
      ),
        AthleticsEvent(
          sName="Decathlon",
          sType = AthleticsEventType.type_combined_events,
          sCategory = AthleticsEventCategory.category_outdoor_male,
          sKey = "decathlon_m_o",
          sCoefficients = hashMapOf("a" to 0.0000009772, "b" to 71186.6785041732, "c" to -5001.0472144005)
        ),
        AthleticsEvent(
          sName="Shot put",
          sType = AthleticsEventType.type_throw,
          sCategory = AthleticsEventCategory.category_outdoor_male,
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

    fun getWindPoints(wind: String):Int {
      wind.toDoubleOrNull().let { if (it!=null) {
        if (it>2.0) {
          return ((-1)*(floor(it*6).toInt()))
        }
        if (it<0) {
          return ((-1)*(floor(it*6).toInt()))
        }
      }
        return 0
      }
    }
  }

}