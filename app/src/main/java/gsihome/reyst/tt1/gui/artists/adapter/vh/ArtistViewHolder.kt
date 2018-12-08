package gsihome.reyst.tt1.gui.artists.adapter.vh

import android.support.v7.widget.RecyclerView
import gsihome.reyst.tt1.databinding.ArtistItemBinding
import gsihome.reyst.tt1.domain.entities.Artist

class ArtistViewHolder(private val binding: ArtistItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(artist: Artist, itemClickListener: (artist: Artist) -> Unit) {
        binding.artist = artist

        artist.image?.let { binding.image.setImageBitmap(it) }
        binding.root.setOnClickListener { itemClickListener(artist) }
        binding.executePendingBindings()
    }
}
