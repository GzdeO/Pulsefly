package com.ktln.kotlinpulse.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ktln.kotlinpulse.model.Data


@Dao
interface MusicDAO {

    @Insert
    suspend fun insertAll(vararg data: Data) : List<Long>

    @Query("SELECT * FROM data")
    suspend fun getAllMusic() : List<Data>

    @Query("SELECT * FROM data WHERE uuid = :artistId")
    suspend fun getMusic(artistId:Int) : Data

    @Query("DELETE FROM data")
    suspend fun deleteAllMusic()
}