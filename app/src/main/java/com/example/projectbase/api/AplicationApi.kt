package com.example.projectbase.api

import com.example.projectbase.models.Deals
import retrofit2.http.GET
import io.reactivex.Observable
import retrofit2.Response

interface AplicationApi {

    @GET("deals")
    suspend fun getDeals():Response<List<Deals>>
}