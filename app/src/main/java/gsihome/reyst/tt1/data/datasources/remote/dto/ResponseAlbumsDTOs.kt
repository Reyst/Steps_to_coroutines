package gsihome.reyst.tt1.data.datasources.remote.dto

import com.google.gson.annotations.SerializedName

data class AlbumsResponse(
    @SerializedName("topalbums")
    val topalbums: TopalbumsDto
)

data class TopalbumsDto(
    @SerializedName("album")
    val albums: List<AlbumDto>
)

data class AlbumDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("playcount")
    val playcount: Int,
    @SerializedName("mbid")
    val mbid: String?,
    @SerializedName("url")
    val url: String,
    @SerializedName("artist")
    val artist: ArtistShortDto,
    @SerializedName("image")
    val image: List<ImageDto>
)

data class ArtistShortDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("mbid")
    val mbid: String
)