package com.ktln.kotlinpulse.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ktln.kotlinpulse.model.chartTracks.Data


@Dao
interface TracksDAO {


    // MusicResponse daki Data ile Tracks Datayı karıştırabiliyor. Hata alırsan, buraya bak.

    @Insert
    suspend fun insertAllTracks(vararg data: Data) : List<Long>

    @Query("SELECT * FROM data")
    suspend fun getAllTracks() : List<Data>

    @Query("SELECT * FROM data WHERE uuid =:trackId")
    suspend fun getTrack(trackId : Int) : Data

    @Query("DELETE FROM data")
    suspend fun deleteAllTracks()


}