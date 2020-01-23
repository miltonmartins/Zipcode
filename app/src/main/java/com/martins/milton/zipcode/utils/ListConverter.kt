package com.martins.milton.zipcode.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.martins.milton.zipcode.data.models.Address
import java.lang.reflect.Type
import java.util.*

class ListConverter {
    var gson = Gson()

    @TypeConverter
    fun stringToList(data: String?): List<Address> {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type =
            object : TypeToken<List<Address?>?>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun listToString(someObjects: List<Address?>?): String {
        return gson.toJson(someObjects)
    }
}