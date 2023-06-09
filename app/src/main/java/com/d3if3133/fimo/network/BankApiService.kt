package com.d3if3133.fimo.network

import com.d3if3133.fimo.model.Bank
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL =
    "https://gist.githubusercontent.com/alfinabalqis/8cea2d84b46aa07ed3833fe972f5e891/raw/ec593055cb72dcec03ca8b5431d5648384f5baac/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface BankApiService {
    @GET("bank.json")
    suspend fun getBank(): List<Bank>
}

object BankApi{
    val service: BankApiService by lazy {
        retrofit.create(BankApiService::class.java)
    }

    fun getBankURL(imageId: String): String {
        return "$BASE_URL$imageId.png"
    }
}

enum class ApiStatus { LOADING, SUCCESS, FAILED }