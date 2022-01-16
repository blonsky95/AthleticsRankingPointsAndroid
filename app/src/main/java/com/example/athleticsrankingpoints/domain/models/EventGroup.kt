package com.example.athleticsrankingpoints.domain.models

class EventGroup (
  val sName:String,
  val sMainEvent: AthleticsEvent,
  val sSimilarEvents: List<AthleticsEvent>,
  val sSex:AthleticsEventSex,
  val sMinNumberPerformancesGroup: Int,
  val sMinNumberPerformancesMainEvent: Int
 ){

  companion object{
    const val sSexMale = "male"
    const val sSexFemale = "female"

    val DEFAULT_GROUP = "Men´s 100m"

    fun getSampleEventGroup(): EventGroup {
      return EventGroup(
        sName="Sample group",
        sMainEvent = AthleticsEvent.getSampleEvent(),
        sSimilarEvents  = listOf(),
        sSex = AthleticsEventSex.Male,
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