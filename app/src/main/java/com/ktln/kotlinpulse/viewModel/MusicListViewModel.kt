package com.ktln.kotlinpulse.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.ktln.kotlinpulse.db.MusicDatabase
import com.ktln.kotlinpulse.model.Data
import com.ktln.kotlinpulse.model.MusicResponse
import com.ktln.kotlinpulse.service.MusicAPIService
import com.ktln.kotlinpulse.utils.OzelSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class MusicListViewModel(application: Application):BaseViewModel(application) {

    val musics=MutableLiveData<List<Data>>()
    val musicError=MutableLiveData<Boolean>()
    val musicLoading=MutableLiveData<Boolean>()

    private var guncellemeZamani=10 * 60 * 1000 * 1000 * 1000L

    private val musicAPIService = MusicAPIService()
    private val disposable=CompositeDisposable()

    private val ozelSharedPreferences = OzelSharedPreferences(getApplication())

    fun refreshData(artistId:Int){

        val kaydedilmeZamani=ozelSharedPreferences.zamaniAl()
        if (kaydedilmeZamani != null && kaydedilmeZamani != 0L && System.nanoTime() - kaydedilmeZamani < guncellemeZamani){
            //sqlite dan al
            verileriSqlitedanAl()
        }else{
            verileriInternettenAl(artistId)
        }

    }

    fun refreshFromInternet(artistId: Int){
        verileriInternettenAl(artistId)
    }

    private fun verileriSqlitedanAl(){

        musicLoading.value=true

        launch {
            val musicList=MusicDatabase(getApplication()).musicDAO().getAllMusic()
            musicleriGöster(musicList)
            Toast.makeText(getApplication(),"Müzikler Roomdan alındı.",Toast.LENGTH_LONG).show()
        }
    }

    private fun verileriInternettenAl(artistId:Int) {
        musicLoading.value=true

        disposable.add(
            musicAPIService.getData(artistId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MusicResponse>(){
                    override fun onSuccess(value: MusicResponse) {
                            sqliteSakla(value.data)
                        Toast.makeText(getApplication(),"Müzikler İnternetten alındı.",Toast.LENGTH_LONG).show()

                    }

                    override fun onError(e: Throwable) {
                       musicError.value=true
                        musicLoading.value=false
                        e.printStackTrace()
                    }

                })
        )

    }

    private fun musicleriGöster(musicsList:List<Data>){
        musics.value=musicsList
        musicError.value=false
        musicLoading.value=false
    }

    private fun sqliteSakla(musicList: List<Data>){
        launch {
            val dao= MusicDatabase(getApplication()).musicDAO()
            dao.deleteAllMusic()
            val uuidListesi=dao.insertAll(*musicList.toTypedArray())
            var i=0
            while (i<musicList.size){
                musicList[i].uuid = uuidListesi[i].toInt()
                i = i + 1
            }

            musicleriGöster(musicList)
        }

        ozelSharedPreferences.zamaniKaydet(System.nanoTime())
    }
}