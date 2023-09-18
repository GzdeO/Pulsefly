package com.ktln.kotlinpulse.model.chartAlbums


import com.google.gson.annotations.SerializedName

data class ChartAlbumsResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("total")
    val total: Int
)