package com.tatoeapps.athleticsrankingpoints

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.beust.klaxon.Klaxon
import com.tatoeapps.athleticsrankingpoints.domain.models.CompetitionCategoryGroup
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AssetsReader {

  @Test
  fun testKlaxonReadingCompetitionCategoryJSONformat() {
    Klaxon().parse<CompetitionCategoryGroup>(jsonCompetitionCategoryGroupFile)
  }
}


val jsonCompetitionCategoryGroupFile = """{
        "sName": "5000m, 3000mSC",
        "sCategories": [
            {
                "sName": "OW",
                "sPlacementPoints": [
                    290,
                    260,
                    230,
                    210,
                    190,
                    175,
                    165,
                    155,
                    110,
                    100,
                    90,
                    80,
                    75,
                    70,
                    65,
                    60
                ]
            }
        ]
    }"""