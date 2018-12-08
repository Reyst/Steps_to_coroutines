package gsihome.reyst.tt1.data.datasources.remote.dto

import com.google.gson.annotations.SerializedName

data class ArtistsResponse(
    @SerializedName("topartists")
    val topartists: TopArtistsDto
)

data class TopArtistsDto(
    @SerializedName("artist")
    val artists: List<ArtistDto>
)

data class ArtistDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("listeners")
    val listeners: String,
    @SerializedName("mbid")
    val mbid: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("streamable")
    val streamable: String,
    @SerializedName("image")
    val image: List<ImageDto>
)
