package gsihome.reyst.tt1.gui.albums.adapter.vh

import android.support.v7.widget.RecyclerView
import gsihome.reyst.tt1.databinding.AlbumItemBinding
import gsihome.reyst.tt1.domain.entities.Album

class AlbumViewHolder(private val binding: AlbumItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(album: Album) {
        binding.album = album
        album.image?.let { binding.image.setImageBitmap(it) }
        binding.executePendingBindings()
    }
}