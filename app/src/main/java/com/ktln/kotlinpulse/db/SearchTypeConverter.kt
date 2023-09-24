package com.ktln.kotlinpulse.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.ktln.kotlinpulse.model.search.Album
import com.ktln.kotlinpulse.model.search.Artist

class SearchTypeConverter {
    @TypeConverter
    fun fromAlbum(album: Album): String {
        return Gson().toJson(album)
    }

    @TypeConverter
    fun toAlbum(json: String): Album {
        return Gson().fromJson(json, Album::class.java)
    }
}

class SearchArtistTypeConverter {
    @TypeConverter
    fun fromArtist(artist: Artist): String {
        return Gson().toJson(artist)
    }

    @TypeConverter
    fun toArtist(json: String): Artist {
        return Gson().fromJson(json, Artist::class.java)
    }
}