package gsihome.reyst.tt1.gui.albums.adapter

import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import gsihome.reyst.tt1.R
import gsihome.reyst.tt1.domain.entities.Album
import gsihome.reyst.tt1.gui.albums.adapter.vh.AlbumViewHolder
import gsihome.reyst.tt1.gui.albums.adapter.vh.ImageViewHolder

class AlbumsAdapter(
    private val image: Bitmap?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items: MutableList<Album> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        return if (viewType == TYPE_IMAGE) {
            ImageViewHolder(
                DataBindingUtil.inflate(
                    inflater,
                    R.layout.image_item,
                    parent,
                    false
                )
            )
        } else {
            AlbumViewHolder(
                DataBindingUtil.inflate(
                    inflater,
                    R.layout.album_item,
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount(): Int = items.size + 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == 0 && holder is ImageViewHolder) {
            holder.bind(image)
        }

        if (position > 0 && holder is AlbumViewHolder) {
            holder.bind(items[position - 1])
        }
    }

    override fun getItemViewType(position: Int): Int = if (position == 0) TYPE_IMAGE else TYPE_ALBUM

    fun updateItems(newItems: List<Album>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    companion object {
        private const val TYPE_IMAGE = 1
        private const val TYPE_ALBUM = 2
    }
}
