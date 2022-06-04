package com.tatoeapps.athleticsrankingpoints.data

import androidx.room.TypeConverter
import com.tatoeapps.athleticsrankingpoints.domain.models.AthleticsEvent
import com.tatoeapps.athleticsrankingpoints.domain.models.EventGroup
import com.tatoeapps.athleticsrankingpoints.domain.models.PerformanceUnitsAware
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tatoeapps.athleticsrankingpoints.domain.models.CompetitionCategoryData
import com.tatoeapps.athleticsrankingpoints.presentation.components.PerformancePlacementDetails
import java.util.*
import kotlin.collections.HashMap

class DataConverter {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun performanceUnitsAwareToJsonString(value: List<PerformanceUnitsAware>): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToPerformanceUnitsAware(value: String) : List<PerformanceUnitsAware> =
        Gson().fromJson(value, object : TypeToken<List<PerformanceUnitsAware>>() {}.type)

    @TypeConverter
    fun performancePlacementDetailsToJsonString(value: List<PerformancePlacementDetails>): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToPerformancePlacementDetails(value: String) : List<PerformancePlacementDetails> =
        Gson().fromJson(value, object : TypeToken<List<PerformancePlacementDetails>>() {}.type)

    @TypeConverter
    fun competitionCategoryToJsonString(value: List<CompetitionCategoryData>): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToCompetitionCategory(value: String) : List<CompetitionCategoryData> =
        Gson().fromJson(value, object : TypeToken<List<CompetitionCategoryData>>() {}.type)

    @TypeConverter
    fun listToJsonString(value: List<String>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()

    @TypeConverter
    fun athleticsEventToJsonString(value: AthleticsEvent): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToAthleticsEvent(value: String) = Gson().fromJson(value, AthleticsEvent::class.java)

    @TypeConverter
    fun eventGroupToJsonString(value: EventGroup): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToEventGroup(value: String) = Gson().fromJson(value, EventGroup::class.java)

    @TypeConverter
    fun athleticsEventArrayListToJsonString(value: List<AthleticsEvent>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToAthleticsEventArrayList(value: String) : List<AthleticsEvent> =
        Gson().fromJson(value, object : TypeToken<List<AthleticsEvent>>() {}.type)

    @TypeConverter
    fun hashmapStringDoubleToString(value: HashMap<String, Double>): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToHashmapStringDouble(value: String) : HashMap<String, Double> =
        Gson().fromJson(value, object : TypeToken<HashMap<String, Double>>() {}.type)

}