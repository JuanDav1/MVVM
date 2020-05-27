package com.example.projectbase.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.projectbase.db.dao.DataBaseDao
import com.example.projectbase.models.Deals
import com.example.projectbase.utils.Converters

@Database(
    entities = [
        Deals::class],
    version = 7,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AplicationDB : RoomDatabase() {
    abstract fun dataBaseDao(): DataBaseDao
}