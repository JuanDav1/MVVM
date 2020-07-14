package com.example.projectbase.api

import retrofit2.Response

class ApiResponse <T> (var status: Status, var response: Response<T>?, var error: Throwable?)