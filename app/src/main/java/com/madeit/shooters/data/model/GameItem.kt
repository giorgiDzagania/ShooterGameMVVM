package com.madeit.shooters.data.model

import com.google.gson.annotations.SerializedName

data class GameItem(
    val id: Int,
    val genre: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val thumbnail: String,
    val title: String,
    @SerializedName("short_description")
    val shortDescription: String = "",
)