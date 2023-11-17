package com.example.mobg5_53204.database.api

import android.util.Log
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ATMDataFetcher {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://opendata.bruxelles.be/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(ATMApi::class.java)

    var atmList = MutableLiveData<List<ATMRecord>>()
    var atmDetail = MutableLiveData<ATMRecord?>()

    fun fetchATMs() {
        api.getATMs().enqueue(object : Callback<ATMResponse> {
            override fun onResponse(call: Call<ATMResponse>, response: Response<ATMResponse>) {
                val atmDataList: MutableList<ATMRecord> = mutableListOf()
                response.body()?.records?.forEach { record ->
                    val atmData = ATMFields(
                        coord = record.fields.coord,
                        agence = record.fields.agence,
                        adresse_fr = record.fields.adresse_fr,
                        adresse_nl = record.fields.adresse_nl
                    )
                    val atmRecord = ATMRecord(
                        recordid = record.recordid,
                        fields = atmData
                    )
                    atmDataList.add(atmRecord)
                }
                atmList.value = atmDataList
            }

            override fun onFailure(call: Call<ATMResponse>, t: Throwable) {
                // Handle errors
                Log.e("ATMDataFetcher", "Connection failed")
                atmList.value = emptyList()
            }
        })
    }

    fun fetchATMById(atmId: String, callback: (ATMFieldsObject?) -> Unit) {
        api.getATMById(atmId).enqueue(object : Callback<ATMFieldsObject> {
            override fun onResponse(
                call: Call<ATMFieldsObject>,
                response: Response<ATMFieldsObject>
            ) {
                Log.d("FETCHER API_RESPONSE", "Received API Response: ${response.raw()}")

                if (response.isSuccessful) {
                    Log.d("FETCHER API_SUCCESS", "Response is successful")
                    Log.d("FETCHER API_SUCCESS", "Response body: ${response.body()}")
                    callback(response.body())
                } else {
                    Log.d("FETCHER API_FAILURE", "Response is not successful: ${response.code()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<ATMFieldsObject>, t: Throwable) {  // Call<ATMFields>
                Log.d("FETCHER API_ERROR", t.message ?: "Unknown error")
                callback(null)
            }
        })
    }

}
