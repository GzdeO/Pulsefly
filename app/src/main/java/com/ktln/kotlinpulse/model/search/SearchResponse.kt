package com.ktln.kotlinpulse.model.search


import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("next")
    val next: String,
    @SerializedName("total")
    val total: Int
)