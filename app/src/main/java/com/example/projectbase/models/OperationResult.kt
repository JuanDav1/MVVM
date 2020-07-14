package com.example.projectbase.models

sealed class OperationResult<out T> {
    data class Success<T>(val data: List<T>?) : OperationResult<T>()
    data class Error(val exception:Exception?) : OperationResult<Nothing>()
}