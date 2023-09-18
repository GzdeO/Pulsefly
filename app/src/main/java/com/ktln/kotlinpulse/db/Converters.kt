package com.ktln.kotlinpulse.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ktln.kotlinpulse.model.Album
import com.ktln.kotlinpulse.model.Artist
import com.ktln.kotlinpulse.model.Contributor

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromContributorsList(value: List<Contributor>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toContributorsList(value: String): List<Contributor> {
        val listType = object : TypeToken<List<Contributor>>() {}.type
        return gson.fromJson(value, listType)
    }


}

class AlbumConverter {
    @TypeConverter
    fun fromAlbum(album: Album): String {
        // Burada Album nesnesini JSON formatına dönüştür
        val gson = Gson()
        return gson.toJson(album)
    }

    @TypeConverter
    fun toAlbum(json: String): Album {
        // Burada JSON formatındaki veriyi Album nesnesine dönüştür
        val gson = Gson()
        return gson.fromJson(json, Album::class.java)
    }
}

class ArtistConverter {
    @TypeConverter
    fun fromArtist(artist: Artist): String {
        // Burada Artist nesnesini JSON formatına dönüştür
        val gson = Gson()
        return gson.toJson(artist)
    }

    @TypeConverter
    fun toArtist(json: String): Artist {
        // Burada JSON formatındaki veriyi Artist nesnesine dönüştür
        val gson = Gson()
        return gson.fromJson(json, Artist::class.java)
    }
}