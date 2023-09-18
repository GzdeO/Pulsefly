package com.ktln.kotlinpulse.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.ktln.kotlinpulse.db.MusicDatabase
import com.ktln.kotlinpulse.model.Data
import kotlinx.coroutines.launch

class MusicDetailViewModel(application: Application): BaseViewModel(application) {

    val musicDetailLiveData=MutableLiveData<Data>()

    fun roomVerisiniAl(uuid:Int){
        launch {
            val dao = MusicDatabase(getApplication()).musicDAO()
            val music=dao.getMusic(uuid)
            musicDetailLiveData.value=music
        }
    }
}