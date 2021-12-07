package com.example.nuntium.db.converter

import androidx.room.TypeConverter
import com.example.nuntium.response.Source

class Converters {

    @TypeConverter
    fun toJson(value: Source): String {
        return value.name
    }


    @TypeConverter
    fun fromJson(string: String): Source {
        return Source(string, string)
    }
}