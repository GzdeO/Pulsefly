package com.ktln.kotlinpulse.model.chartTracks


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.ktln.kotlinpulse.db.AlbumTypeConverter
import com.ktln.kotlinpulse.db.TracksConverters


@Entity
@TypeConverters(TracksConverters::class, AlbumTypeConverter::class)
data class Data(
    @ColumnInfo(name = "album")
    @SerializedName("album")
    val album: Album,

    @ColumnInfo(name = "artist")
    @SerializedName("artist")
    val artist: Artist,

    @ColumnInfo(name = "duration")
    @SerializedName("duration")
    val duration: Int,

    @ColumnInfo(name = "explicit_content_cover")
    @SerializedName("explicit_content_cover")
    val explicitContentCover: Int,

    @ColumnInfo(name = "explicit_content_lyrics")
    @SerializedName("explicit_content_lyrics")
    val explicitContentLyrics: Int,

    @ColumnInfo(name = "explicit_lyrics")
    @SerializedName("explicit_lyrics")
    val explicitLyrics: Boolean,

    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: Long,

    @ColumnInfo(name = "link")
    @SerializedName("link")
    val link: String,

    @ColumnInfo(name = "md5_image")
    @SerializedName("md5_image")
    val md5İmage: String,

    @ColumnInfo(name = "position")
    @SerializedName("position")
    val position: Int,

    @ColumnInfo(name = "preview")
    @SerializedName("preview")
    val preview: String,

    @ColumnInfo(name = "rank")
    @SerializedName("rank")
    val rank: Int,

    @ColumnInfo(name = "title")
    @SerializedName("title")
    val title: String,

    @ColumnInfo(name = "title_short")
    @SerializedName("title_short")
    val titleShort: String,

    @ColumnInfo(name = "title_version")
    @SerializedName("title_version")
    val titleVersion: String,

    @ColumnInfo(name = "type")
    @SerializedName("type")
    val type: String
){
    @PrimaryKey(autoGenerate = true)
    var uuid : Int =0
}