package com.ktln.kotlinpulse.service

import com.ktln.kotlinpulse.model.MusicResponse
import com.ktln.kotlinpulse.model.chartTracks.ChartTracksResponse
import com.ktln.kotlinpulse.model.search.SearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MusicAPI {

    //https://api.deezer.com/artist/27/top?limit=50


    @GET("artist/{id}/top?limit=50")
    fun getMusic(
        @Path("id") artistId: Int?
    ) : Single<MusicResponse>


    //https://api.deezer.com/chart/0/tracks

    @GET("chart/{id}/tracks")
    fun getChartTracks(
        @Path("id") trackId: String?
    ) : Single<ChartTracksResponse>


    //https://api.deezer.com/search?q=eminem


    @GET("search/track")
    fun searchMusics(
        @Query("q") trackName: String
    ): Single<SearchResponse>


}