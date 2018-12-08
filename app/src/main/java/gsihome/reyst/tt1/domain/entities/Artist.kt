package gsihome.reyst.tt1.domain.entities

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Artist(
    val id: String,
    val name: String,
    val count: Int,
    val image: Bitmap?
) : Comparable<Artist>, Parcelable {
    override fun compareTo(other: Artist): Int = name.compareTo(other.name)
}