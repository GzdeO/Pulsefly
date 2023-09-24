package com.ktln.kotlinpulse.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.ktln.kotlinpulse.model.chartTracks.Album
import com.ktln.kotlinpulse.model.chartTracks.Artist

class TracksConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromArtist(artist: Artist): String {
        return gson.toJson(artist)
    }

    @TypeConverter
    fun toArtist(artistJson: String): Artist {
        return gson.fromJson(artistJson, Artist::class.java)
    }
}

class AlbumTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromAlbum(album: Album): String {
        return gson.toJson(album)
    }

    @TypeConverter
    fun toAlbum(albumJson: String): Album {
        return gson.fromJson(albumJson, Album::class.java)
    }
}