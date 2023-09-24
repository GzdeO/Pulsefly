package com.ktln.kotlinpulse.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ktln.kotlinpulse.db.TracksDatabase
import com.ktln.kotlinpulse.model.chartTracks.Data
import kotlinx.coroutines.launch

class TrackDetailViewModel(application: Application) : BaseViewModel(application) {
    val trackDetailLiveData=MutableLiveData<Data>()

    fun roomVerisiniAl(uuid : Int){

        launch {
            val dao=TracksDatabase(getApplication()).tracksDAO()
            val track=dao.getTrack(uuid)
            trackDetailLiveData.value=track

        }
    }
}