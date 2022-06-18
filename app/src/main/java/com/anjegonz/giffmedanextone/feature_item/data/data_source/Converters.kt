package com.anjegonz.giffmedanextone.feature_item.data.data_source

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun getList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson<List<String>>(value, listType)
    }

    @TypeConverter
    fun listToString(list: List<String>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}