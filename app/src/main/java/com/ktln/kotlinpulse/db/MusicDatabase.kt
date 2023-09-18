package com.ktln.kotlinpulse.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ktln.kotlinpulse.model.Data


@Database(entities = arrayOf(Data::class), version = 1, exportSchema = false)
abstract class MusicDatabase : RoomDatabase() {

    abstract fun musicDAO (): MusicDAO


    companion object{

        @Volatile private var instance: MusicDatabase?=null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            instance ?: databaseOlustur(context).also {
                instance=it
            }
        }


        private fun databaseOlustur(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            MusicDatabase::class.java,
            "music_db").build()
    }



}