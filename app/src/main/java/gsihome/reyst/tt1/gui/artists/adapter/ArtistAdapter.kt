package gsihome.reyst.tt1.gui.artists.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import gsihome.reyst.tt1.R
import gsihome.reyst.tt1.domain.entities.Artist
import gsihome.reyst.tt1.gui.artists.adapter.vh.ArtistViewHolder


class ArtistAdapter(
    private val itemClickListener: (artist: Artist) -> Unit
) : RecyclerView.Adapter<ArtistViewHolder>() {

    private val items: MutableList<Artist> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): ArtistViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ArtistViewHolder(
            DataBindingUtil.inflate(
                inflater,
                R.layout.artist_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        holder.bind(items[position], itemClickListener)
    }

    fun updateItems(newItems: List<Artist>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}