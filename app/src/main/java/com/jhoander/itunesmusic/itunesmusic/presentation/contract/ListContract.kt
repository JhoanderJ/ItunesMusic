package com.jhoander.itunesmusic.itunesmusic.presentation.contract

import android.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.jhoander.itunesmusic.base.presentation.BaseView
import com.jhoander.itunesmusic.base.presentation.PresenterBase
import com.jhoander.itunesmusic.itunesmusic.data.domain.entity.ItunesItem
import com.jhoander.itunesmusic.itunesmusic.presentation.adapter.ItunesTrackPagedAdapter

interface ListContract {

    interface ListView: BaseView{
        fun getVMAndObserve(
            term: String,
            observer: Observer<PagedList<ItunesItem>>
        ): LiveData<PagedList<ItunesItem>>

        fun goToAlbumDetail(id:Long)
    }

    interface ListPresenter: PresenterBase<ListView> {
        fun initAdapterListener()
        fun getSearchBarListener(): SearchView.OnQueryTextListener
        fun getAdapter(): ItunesTrackPagedAdapter
        fun stopTrack()
    }
}