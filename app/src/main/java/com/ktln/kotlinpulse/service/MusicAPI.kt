package com.ktln.kotlinpulse.service

import com.ktln.kotlinpulse.model.MusicResponse
import com.ktln.kotlinpulse.model.chartAlbums.ChartAlbumsResponse
import com.ktln.kotlinpulse.model.chartTracks.ChartTracksResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface MusicAPI {

    //https://api.deezer.com/artist/27/top?limit=50


    @GET("artist/{id}/top?limit=50")
    fun getMusic(
        @Path("id") artistId: Int?
    ) : Single<MusicResponse>


    //https://api.deezer.com/chart/0/tracks

    @GET("chart/{id}/tracks")
    fun getChartTracks(
        @Path("id") trackId: Int?
    ) : Single<ChartTracksResponse>



    //https://api.deezer.com/chart/0/albums

    @GET("chart/{id}/albums")
    fun getChartAlbums(
        @Path("id") albumId: Int?
    ) : Single<ChartAlbumsResponse>
}