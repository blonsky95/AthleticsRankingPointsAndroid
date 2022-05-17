package com.tatoeapps.athleticsrankingpoints.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tatoeapps.athleticsrankingpoints.data.database.EVENT_GROUPS_TABLE_NAME

@Entity(tableName = EVENT_GROUPS_TABLE_NAME)
class EventGroup (
  @PrimaryKey
  val sName:String,
  val sMainEvent: AthleticsEvent,
  val sSimilarEvents: List<AthleticsEvent>,
  val sSex:AthleticsSex,
  val sMinNumberPerformancesGroup: Int,
  val sMinNumberPerformancesMainEvent: Int
 ){

  companion object{
    val DEFAULT_GROUP = "Men´s 100m"

    fun getSampleEventGroup(): EventGroup {
      return EventGroup(
        sName="Sample group",
        sMainEvent = AthleticsEvent.getSampleEvent(),
        sSimilarEvents  = listOf(),
        sSex = AthleticsSex.Male,
        sMinNumberPerformancesMainEvent = 3,
        sMinNumberPerformancesGroup = 5
      )
    }
  }

  fun getAllEventsInGroup(): ArrayList<AthleticsEvent> {
    //    list.add(0,sMainEvent)
    return ArrayList(sSimilarEvents).apply<ArrayList<AthleticsEvent>> { add(0, sMainEvent) }
  }

}