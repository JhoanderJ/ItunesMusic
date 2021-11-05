package com.jhoander.itunesmusic.itunesmusic.presentation.contract

import com.jhoander.itunesmusic.base.presentation.BaseView
import com.jhoander.itunesmusic.base.presentation.PresenterBase
import com.jhoander.itunesmusic.itunesmusic.data.domain.entity.ItunesItem
import com.jhoander.itunesmusic.itunesmusic.presentation.adapter.ItunesTrackAdapter

interface AlbumDetailContract {
    interface AlbumDetailView: BaseView{
        fun showAlbumInfo(album: ItunesItem)
    }

    interface AlbumDetailPresenter: PresenterBase<AlbumDetailView>{
        fun initAdapterListener()
        fun getAdapter(): ItunesTrackAdapter
        fun findAlbumTracks(id: Long)
        fun stopTrack()
    }
}