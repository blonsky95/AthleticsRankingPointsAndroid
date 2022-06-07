package com.tatoeapps.athleticsrankingpoints.domain.models

data class PerformancePlacementDetails(
  val competitionCategoryGroup: CompetitionCategoryGroup,
  var totalPoints: String? = null,
  val category: CompetitionCategory? = null,
  val position: Int? = null,
) {

  fun updateCategory(category: CompetitionCategory) : PerformancePlacementDetails {
    val newPosition = this.position?.takeIf { it <= competitionCategoryGroup.getPositionsForCategory(category).size } ?: competitionCategoryGroup.getPositionsForCategory(category).last()
    return this.copy(
      totalPoints = competitionCategoryGroup.getPointsForPosition(newPosition, category).toString(),
      position = newPosition,
      category = category
    )
  }

  fun updatePosition(position: Int) = this.copy(
    totalPoints = competitionCategoryGroup.getPointsForPosition(position, this.category ?: this.competitionCategoryGroup.getFirstCategory()).toString(),
    position = position
  )
}
