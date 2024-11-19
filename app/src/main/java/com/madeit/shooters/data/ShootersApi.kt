package com.madeit.shooters.data

import com.madeit.shooters.data.model.GameItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ShootersApi {

    @GET("games")
    suspend fun getAllFreeShooterGames(): Response<List<GameItem>>

    @GET("game")
    suspend fun getGameById(@Query("id") gameId: String): Response<GameItem>

}