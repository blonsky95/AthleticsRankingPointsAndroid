package com.example.athleticsrankingpoints.data

import androidx.room.TypeConverter
import com.example.athleticsrankingpoints.domain.models.AthleticsEvent
import com.example.athleticsrankingpoints.domain.models.EventGroup
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataConverter {

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