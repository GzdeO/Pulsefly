package com.ktln.kotlinpulse.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ktln.kotlinpulse.db.TracksDatabase
import com.ktln.kotlinpulse.model.chartTracks.ChartTracksResponse
import com.ktln.kotlinpulse.model.chartTracks.Data
import com.ktln.kotlinpulse.service.MusicAPIService
import com.ktln.kotlinpulse.utils.OzelSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class TracksListViewModel(application: Application): BaseViewModel(application) {

    val tracks= MutableLiveData<List<Data>>()
    val trackError=MutableLiveData<Boolean>()
    val trackLoading=MutableLiveData<Boolean>()

    private var guncellemeZamani=10 * 60 * 1000 * 1000 * 1000L

    private val tracksApiService=MusicAPIService()
    private val disposable=CompositeDisposable()

    private val ozelSharedPreferences = OzelSharedPreferences(getApplication())


    fun refreshTrackData(trackId:String){

        val kaydedilmeZamani= ozelSharedPreferences.zamaniAl()

        if (kaydedilmeZamani != null && kaydedilmeZamani != 0L && System.nanoTime() - kaydedilmeZamani < guncellemeZamani){
            //sqlite dan al

            verileriSqlitetanAl()

        }else{
            verileriInternettenAl(trackId)
        }

    }


    fun refreshFromInternet(trackId : String){
        verileriInternettenAl(trackId)
    }

    private fun verileriSqlitetanAl() {
        trackLoading.value=true

        launch {
            val tracksList= TracksDatabase(getApplication()).tracksDAO().getAllTracks()
            trackslariGoster(tracksList)
        }
    }

    private fun verileriInternettenAl(trackId:String) {
        trackLoading.value=true

        disposable.add(
            tracksApiService.getTracksData(trackId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ChartTracksResponse>(){
                    override fun onSuccess(value: ChartTracksResponse?) {
                        sqliteSakla(value!!.data)
                    }

                    override fun onError(e: Throwable?) {
                        trackError.value=true
                        trackLoading.value=false
                        e?.printStackTrace()
                    }

                })
        )

    }

    private fun trackslariGoster(trackListesi : List<Data>){
        tracks.value=trackListesi
        trackError.value=false
        trackLoading.value=false
    }

    private fun  sqliteSakla(trackListesi: List<Data>){

        launch {
            val dao=TracksDatabase(getApplication()).tracksDAO()
            dao.deleteAllTracks()
            val uuidTrackListesi=dao.insertAllTracks(*trackListesi.toTypedArray())

            var i=0

            while (i<trackListesi.size){
                trackListesi[i].uuid = uuidTrackListesi[i].toInt()
                i=i+1
            }
            trackslariGoster(trackListesi)
        }

        ozelSharedPreferences.zamaniKaydet(System.nanoTime())

    }


}