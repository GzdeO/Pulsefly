package com.ktln.kotlinpulse.service

import com.ktln.kotlinpulse.model.MusicResponse
import com.ktln.kotlinpulse.utils.Constants.BASE_URL
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MusicAPIService {
    //https://api.deezer.com/artist/27/top?limit=50

    private val api=Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(MusicAPI::class.java)

    fun getData(artistId:Int) : Single<MusicResponse>{
        return api.getMusic(artistId)
    }
}