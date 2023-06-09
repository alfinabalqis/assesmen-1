package com.d3if3133.fimo.network

import com.d3if3133.fimo.model.Bank
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://raw.githubusercontent.com/" +
        "alfinabalqis/fimo/static-api/"

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