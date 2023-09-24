package com.ktln.kotlinpulse.viewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ktln.kotlinpulse.db.SearchDatabase
import com.ktln.kotlinpulse.model.search.Data
import com.ktln.kotlinpulse.model.search.SearchResponse
import com.ktln.kotlinpulse.service.MusicAPIService
import com.ktln.kotlinpulse.utils.OzelSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class SearchViewModel(application: Application) : BaseViewModel(application) {

    val searchedMusicLiveData=MutableLiveData<List<Data>>()

    private var guncellemeZamani=10 * 60 * 1000 * 1000 * 1000L

    private val searchApiService= MusicAPIService()
    private val disposable= CompositeDisposable()

    private val ozelSharedPreferences = OzelSharedPreferences(getApplication())



fun refreshData(trackName:String){

    val kaydedilmeZamani=ozelSharedPreferences.zamaniAl()
    if (kaydedilmeZamani != null && kaydedilmeZamani != 0L && System.nanoTime() - kaydedilmeZamani < guncellemeZamani){
        //sqlite dan al

        verileriSqlitetanAl()

    }else{
        verileriInternettenAl(trackName)
    }
}


    private fun verileriSqlitetanAl() {
        launch {
            val searchListesi = SearchDatabase(getApplication()).searchDao().getAllSearch()
            searchGoster(searchListesi)

        }
    }


    fun verileriInternettenAl(trackName:String){
        disposable.add(
            searchApiService.getSearchData(trackName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<SearchResponse>(){
                    override fun onSuccess(value: SearchResponse?) {
                        val musicList=value?.data
                        musicList?.let {
                            sqliteSakla(value?.data)
                        }
                    }

                    override fun onError(e: Throwable?) {
                        Log.e("SearchViewModel",e?.message.toString())
                    }

                })
        )
    }


    private fun searchGoster(searchListesi: List<Data>){
        searchedMusicLiveData.postValue(searchListesi)
    }

    private fun sqliteSakla(searchList : List<Data>){
        launch {
            val dao=SearchDatabase(getApplication()).searchDao()
            dao.deleteAllSearch()
            val uuidSearchListesi=dao.insertAllSearch(*searchList.toTypedArray())
            var i=0
            while (i<searchList.size){
                searchList[i].uuid = uuidSearchListesi[i].toInt()
                i=i+1
            }

            searchGoster(searchList)
        }

        ozelSharedPreferences.zamaniKaydet(System.nanoTime())
    }



}