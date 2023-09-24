package com.ktln.kotlinpulse.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ktln.kotlinpulse.model.search.Data


@Dao
interface SearchDAO {

    @Insert
    suspend fun insertAllSearch(vararg data:Data) : List<Long>

    @Query("SELECT * FROM data")
    suspend fun getAllSearch() : List<Data>

    @Query("SELECT * FROM data WHERE uuid =:searchId")
    suspend fun getSearch(searchId: Int) : Data

    @Query("DELETE FROM data")
    suspend fun deleteAllSearch()
}