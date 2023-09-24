package com.ktln.kotlinpulse.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.ktln.kotlinpulse.db.SearchDatabase
import com.ktln.kotlinpulse.model.search.Data
import kotlinx.coroutines.launch

class SearchDetailViewModel(application: Application): BaseViewModel(application) {


    val searchLiveData = MutableLiveData<Data>()

    fun roomVerisiniAl(uuid:Int){
        launch {
            val dao=SearchDatabase(getApplication()).searchDao()
            val search=dao.getSearch(uuid)
            searchLiveData.value=search
        }
    }
}