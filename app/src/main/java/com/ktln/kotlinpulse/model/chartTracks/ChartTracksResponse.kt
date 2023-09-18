package com.ktln.kotlinpulse.model.chartTracks


import com.google.gson.annotations.SerializedName

data class ChartTracksResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("total")
    val total: Int
)