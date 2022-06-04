package com.tatoeapps.athleticsrankingpoints.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import arrow.core.prependTo
import com.tatoeapps.athleticsrankingpoints.data.database.COMPETITION_CATEGORY_TABLE_NAME

@Entity(tableName = COMPETITION_CATEGORY_TABLE_NAME)
data class CompetitionCategoryGroup(
  @PrimaryKey
  val sName: String,
  val sCategories: List<CompetitionCategoryData>,
) {

  companion object {
    fun getDefault() = CompetitionCategoryGroup("OTHER", listOf(CompetitionCategoryData(CompetitionCategory.OTHER.name, listOf(0))))
  }

  fun getPositionsForCategory(competitionCategory: CompetitionCategory) =
    (sCategories.findCategory(competitionCategory)?.sPlacementPoints?.mapIndexed { index, _ -> index + 1 } ?: listOf(1, 2, 3)).addZeroToStart()

  fun List<Int>.addZeroToStart(): List<Int> {
    val mutList = this.toMutableList()
    mutList.add(0, 0)
    return mutList.toList()
  }

  fun getPointsForPosition(position: Int, competitionCategory: CompetitionCategory) =
    sCategories.findCategory(competitionCategory)?.takeIf { position > 0 }?.let { it.sPlacementPoints[position - 1] } ?: 0

}

fun List<CompetitionCategoryData>.findCategory(competitionCategory: CompetitionCategory) = this.find { competitionCategory == CompetitionCategory.valueOf(it.sName) }

data class CompetitionCategoryData(
  val sName: String,
  val sPlacementPoints: List<Int>,
)

enum class CompetitionCategoryEventGroup(val eventGroupId: String) {
  MAIN("Main"),
  LONG_DISTANCE_1("5000m, 3000mSC"),
  LONG_DISTANCE_2("10000m"),
  COMBINED_EVENTS("Combined Events"),
  MARATHON("Marathon"),
  ROAD_RUNNING("Road running");

  companion object {
    fun getEnumFromEventGroupId(id: String) =
      when (id) {
        "Main" -> MAIN
        "5000m, 3000mSC" -> LONG_DISTANCE_1
        "10000m" -> LONG_DISTANCE_2
        "Combined Events" -> COMBINED_EVENTS
        "Marathon" -> MARATHON
        "Road running" -> ROAD_RUNNING
        else -> MAIN
      }
  }
}

enum class CompetitionCategory(val categoryName: String) {
  OW("OW"),
  DF("DF"),
  GW("GW"),
  GL("GL"),
  A("A"),
  B("B"),
  C("C"),
  D("D"),
  E("E"),
  F("F"),
  OTHER("Other")
}
