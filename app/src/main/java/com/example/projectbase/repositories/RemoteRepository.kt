package com.example.projectbase.repositories

import com.example.projectbase.api.AplicationApi
import com.example.projectbase.models.Deals
import com.example.projectbase.models.OperationResult

class RemoteRepository(private var aplicationApiCall: AplicationApi) {


    suspend fun getDeals(): OperationResult<Deals> {
        try {
            val response = aplicationApiCall.getDeals()
            response?.let {
                return if (it.isSuccessful && it.body() != null) {
                    val data = it.body()
                    OperationResult.Success(data)
                }else{
                    OperationResult.Error(Exception("Ocurrió un error"))
                }
            }
        } catch (e: Exception) {
            return OperationResult.Error(e)
        }

    }
}