package com.example.athleticsrankingpoints.domain

class EventGroup (
  val sName:String,
  val sMainEvent: AthleticsEvent,
  val sSimilarEvents: ArrayList<AthleticsEvent>,
  val sSex:String,
  val sMinNumberPerformancesGroup: Int,
  val sMinNumberPerformancesMainEvent: Int
 ){

  companion object{
    const val sSexMale = "male"
    const val sSexFemale = "female"

    fun getSampleEventGroup():EventGroup{
      return EventGroup(
        sName="Sample group",
        sMainEvent = AthleticsEvent.getSampleEvent(),
        sSimilarEvents  = arrayListOf(),
        sSex = AthleticsEvent.sexMale,
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