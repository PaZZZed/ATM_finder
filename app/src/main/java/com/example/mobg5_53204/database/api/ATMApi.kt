package com.example.mobg5_53204.database.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ATMApi {
    @GET("records/1.0/search/?dataset=atms")
    fun getATMs(): Call<ATMResponse>

    @GET("explore/v2.1/catalog/datasets/atms/records/{id}")
    fun getATMById(@Path("id") atmId: String): Call<ATMFieldsObject>
}
