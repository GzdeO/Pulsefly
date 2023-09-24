package com.ktln.kotlinpulse.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ktln.kotlinpulse.model.chartTracks.Data


@Database(entities = [Data::class], version = 1, exportSchema = false)
abstract class TracksDatabase : RoomDatabase() {

    abstract fun tracksDAO() : TracksDAO


    companion object{

        @Volatile private var instance : TracksDatabase? = null

        private val lock=Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            instance ?: trackDatabaseOlustur(context).also {
                instance=it
            }
        }

        private fun trackDatabaseOlustur(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            TracksDatabase::class.java,
            "tracksdatabase").build()
    }


}