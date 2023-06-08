package com.d3if3133.fimo.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://gist.githubusercontent.com/alfinabalqis/8cea2d84b46aa07ed3833fe972f5e891/raw/ec593055cb72dcec03ca8b5431d5648384f5baac/"
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface BankApiService {
    @GET("bank.json")
    suspend fun getBank(): String
}

object BankApi{
    val service: BankApiService by lazy {
        retrofit.create(BankApiService::class.java)
    }
}