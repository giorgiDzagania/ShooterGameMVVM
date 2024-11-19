package com.madeit.shooters.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitInstance {

    private const val BASE_URL = "https://www.freetogame.com/api/"

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun createAllFreeGamesApi(): ShootersApi {
        return createRetrofit().create(ShootersApi::class.java)
    }

}