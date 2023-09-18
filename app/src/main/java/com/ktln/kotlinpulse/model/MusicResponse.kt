package com.ktln.kotlinpulse.model


import com.google.gson.annotations.SerializedName

data class MusicResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("next")
    val next: String,
    @SerializedName("total")
    val total: Int
)