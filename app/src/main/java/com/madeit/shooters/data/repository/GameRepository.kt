package com.madeit.shooters.data.repository

import android.util.Log
import com.madeit.shooters.data.RetrofitInstance
import com.madeit.shooters.data.model.GameItem

class GameRepository {

    private val apiForAllFreeGames = RetrofitInstance.createAllFreeGamesApi()

    suspend fun getPopularGames(): List<GameItem>? {
        return try {
            val response = apiForAllFreeGames.getAllFreeShooterGames()
            Log.d("GameRepository", "${response.body().toString()}")
            Log.d("GameRepository", "${response.message()}")
            if (response.isSuccessful) {
                Log.d("GameRepository", "${response.toString()}")
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            Log.d("GameRepository","${e.message}")
            null
        }
    }
}