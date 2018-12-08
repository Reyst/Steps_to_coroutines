package gsihome.reyst.tt1.gui.albums.adapter.vh

import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import gsihome.reyst.tt1.databinding.ImageItemBinding

class ImageViewHolder(private val binding: ImageItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(image: Bitmap?) {
        image?.also {
            binding.image.setImageBitmap(it)
            binding.executePendingBindings()
        }
    }

}