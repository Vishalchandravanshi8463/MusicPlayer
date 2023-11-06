package com.example.musicplayer

import android.app.Activity
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
class SearchResultRecyclerAdapter(val context: Activity, val dataList: List<Data>) :
    RecyclerView.Adapter<SearchResultRecyclerAdapter.MyViewHolder>() {

    private var player: MediaPlayer? = null
    private var currentData: Data? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.search_result_card, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = dataList[position]
        holder.musicTittle.text = data.title
        Picasso.get().load(data.album.cover).into(holder.musicImage)

        holder.musicPlayBtn.setOnClickListener {
            if (data != currentData) {
                // If a new song is clicked, stop the previous one and start the new one
                stopAndReleasePlayer()
                player = MediaPlayer.create(context, data.preview.toUri())
                player?.start()
                currentData = data
            }
            updateButtonVisibility(holder, true, false, true)
        }

        holder.musicPauseBtn.setOnClickListener {
            player?.pause()
            updateButtonVisibility(holder, true, true, false)
        }

        holder.musicStopBtn.setOnClickListener {
            stopAndReleasePlayer()
            currentData = null
            updateButtonVisibility(holder, true, false, false)
        }
    }

    private fun stopAndReleasePlayer() {
        player?.stop()
        player?.release()
        player = null
    }

    private fun updateButtonVisibility(holder: MyViewHolder, playVisible: Boolean, pauseVisible: Boolean, stopVisible: Boolean) {
        holder.musicPlayBtn.visibility = if (playVisible) View.VISIBLE else View.GONE
        holder.musicPauseBtn.visibility = if (pauseVisible) View.VISIBLE else View.GONE
        holder.musicStopBtn.visibility = if (stopVisible) View.VISIBLE else View.GONE
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val musicImage: ImageView
        val musicTittle: TextView
        val musicPlayBtn: ImageButton
        val musicPauseBtn: ImageButton
        val musicStopBtn: ImageButton

        init {
            musicImage = itemView.findViewById(R.id.musicImage)
            musicTittle = itemView.findViewById(R.id.musicTitle)
            musicPlayBtn = itemView.findViewById(R.id.musicPlayBtn)
            musicPauseBtn = itemView.findViewById(R.id.musicPauseBtn)
            musicStopBtn = itemView.findViewById(R.id.musicStopBtn)
        }
    }
}

