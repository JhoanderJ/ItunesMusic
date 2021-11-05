package com.jhoander.itunesmusic.itunesmusic.presentation.presenter

import android.media.MediaPlayer
import android.os.Handler
import com.jhoander.itunesmusic.itunesmusic.data.domain.entity.ItunesItem
import com.jhoander.itunesmusic.itunesmusic.data.domain.local.repo.ItunesItemRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton class ItunesPlayer @Inject constructor(
    private val itunesItemRepo: ItunesItemRepo
){
    private val refreshTime = 300L

    init {
        player.setOnCompletionListener { stopTrack() }
    }

    fun playTrack(track: ItunesItem){
        stopTrack()

        player.setDataSource(track.previewUrl)
        track.isPlaying = true
        trackPlaying = track

        itunesItemRepo.save(track)

        player.setOnPreparedListener {
            it.start()

            trackPlaying?.let { item ->
                item.playDuration = it.duration
                itunesItemRepo.save(item)

                handler.removeCallbacksAndMessages(null)
                handler.postDelayed(object : Runnable{
                    override fun run() {
                        trackPlaying?.let { item ->
                            item.playedPercent = it.currentPosition
                            itunesItemRepo.save(item)
                            handler.postDelayed(this, refreshTime)
                        }

                    }
                }, refreshTime)
            }
        }

        player.prepareAsync()
    }

    fun stopTrack(){
        if(player.isPlaying){
            player.stop()
        }

        player.reset()

        trackPlaying?.let {
            handler.removeCallbacksAndMessages(null)
            it.isPlaying = false
            it.playedPercent = 0
            it.playDuration = 0
            itunesItemRepo.save(it)
        }

        trackPlaying = null
    }

    companion object{
        private var trackPlaying: ItunesItem? = null
        private val player = MediaPlayer()
        private val handler = Handler()
    }
}
