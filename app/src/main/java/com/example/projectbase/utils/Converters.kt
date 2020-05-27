package com.example.projectbase.utils

import androidx.room.TypeConverter

class Converters {


    companion object {

        @TypeConverter
        @JvmStatic
        fun fromNullToString(value: String?): String? {
            return value ?: ""
        }
    }

}