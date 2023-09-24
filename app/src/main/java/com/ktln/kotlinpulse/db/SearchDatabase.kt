package com.ktln.kotlinpulse.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ktln.kotlinpulse.model.search.Data


@Database(entities = arrayOf(Data::class), version = 2, exportSchema = false)
abstract class SearchDatabase : RoomDatabase() {

    abstract fun searchDao() : SearchDAO

    companion object{

        @Volatile private var instance : SearchDatabase?=null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            instance ?: databaseOlustur(context).also {
                instance = it
            }
        }

        private fun databaseOlustur(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            SearchDatabase::class.java,
            "searchdatabase").build()
    }

}