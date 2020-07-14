package com.example.projectbase.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Observable
import com.example.projectbase.models.Deals


@Dao
interface  DataBaseDao {
    @Insert
    suspend fun insertDeals(deals:List<Deals>)

    @Query("SELECT * FROM deals ")
    suspend fun getDeals():List<Deals>

    @Query("DELETE FROM deals ")
    suspend fun deleteDeals()
}