package gsihome.reyst.tt1.domain.entities

import android.graphics.Bitmap

data class Album(
    val id: String,
    val name: String,
    val count: Int,
    val image: Bitmap?,
    val artistId: String
)