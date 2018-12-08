package gsihome.reyst.tt1.data.datasources.remote.dto

import com.google.gson.annotations.SerializedName

data class ImageDto(
    @SerializedName("#text")
    val url: String,
    @SerializedName("size")
    val size: String
)
