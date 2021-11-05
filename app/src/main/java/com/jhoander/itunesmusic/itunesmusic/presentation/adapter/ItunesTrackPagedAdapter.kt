package com.jhoander.itunesmusic.itunesmusic.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jhoander.itunesmusic.R
import com.jhoander.itunesmusic.base.presentation.ItemAdapterListener
import com.jhoander.itunesmusic.itunesmusic.data.domain.entity.ItunesItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_playstop.view.*
import kotlinx.android.synthetic.main.item_track_music.view.*
import javax.inject.Inject

class ItunesTrackPagedAdapter @Inject constructor(

) : PagedListAdapter<ItunesItem, ItunesTrackPagedAdapter.ViewHolder>(DIFF_CALLBACK) {
    var clickListener: ItemAdapterListener<ItunesItem>? = null
    var playListener: ItemAdapterListener<ItunesItem>? = null

    override fun getItemId(position: Int): Long {
        return getItem(position)!!.trackId.toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_track_music, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.initView(getItem(position), position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun initView(item: ItunesItem?, pos: Int){
            if(item == null){
                itemView.visibility = View.GONE
                return
            }

            itemView.itunesItemName.text = item.trackName
            itemView.itunesItemArtist.text = item.artistName

            item.artworkUrl60?.let {
                if(itemView.itunesItemThumb.tag == it){
                    return@let
                }

                Picasso.get()
                    .load(it)
                    .into(itemView.itunesItemThumb)

                itemView.itunesItemThumb.tag = it
            }

            if(item.isPlaying){
                itemView.playStopButton.setImageResource(R.drawable.ic_stop)

                if(item.playedPercent == 0){
                    itemView.playStopProgress.isIndeterminate = true
                }else{
                    itemView.playStopProgress.isIndeterminate = false
                    itemView.playStopProgress.max = item.playDuration
                    itemView.playStopProgress.progress = item.playedPercent
                }

                itemView.playStopProgress.visibility = View.VISIBLE
            }else{
                itemView.playStopButton.setImageResource(R.drawable.ic_play_arrow)
                itemView.playStopProgress.visibility = View.GONE
            }

            itemView.setOnClickListener {
                clickListener?.onItem(item, pos)
            }

            itemView.playStop.setOnClickListener {
                playListener?.onItem(item, pos)
            }

            itemView.visibility = View.VISIBLE
        }
    }

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<ItunesItem>() {
            override fun areItemsTheSame(oldItem: ItunesItem, newItem: ItunesItem) = oldItem.trackId == newItem.trackId

            override fun areContentsTheSame(oldItem: ItunesItem, newItem: ItunesItem) = oldItem == newItem
        }
    }
}