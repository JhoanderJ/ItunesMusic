package com.jhoander.itunesmusic.itunesmusic.presentation.presenter

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.jhoander.itunesmusic.base.presentation.ItemAdapterListener
import com.jhoander.itunesmusic.itunesmusic.data.domain.entity.ItunesItem
import com.jhoander.itunesmusic.itunesmusic.presentation.adapter.ItunesTrackPagedAdapter
import com.jhoander.itunesmusic.itunesmusic.presentation.contract.ListContract
import javax.inject.Inject
import javax.inject.Singleton


@Singleton class ListPresenter @Inject constructor(private val trackPagedAdapter: ItunesTrackPagedAdapter, private val itunesPlayer: ItunesPlayer): ListContract.ListPresenter {
    private val trackObserver = Observer<PagedList<ItunesItem>> { trackPagedAdapter.submitList(it) }
    private var itemPlaying: Long? = null

    private lateinit var view: ListContract.ListView

    private var trackLiveData: LiveData<PagedList<ItunesItem>>? = null

    override fun setView(view: ListContract.ListView) {
        this.view = view
    }

    override fun initAdapterListener() {
        trackPagedAdapter.playListener = ItemAdapterListener { item, _ ->
            if(itemPlaying == item.trackId){
                itunesPlayer.stopTrack()
                itemPlaying = null
            }else {
                itemPlaying = item.trackId
                itunesPlayer.playTrack(item)
            }
        }

        trackPagedAdapter.clickListener = ItemAdapterListener { item, _ ->
            itunesPlayer.stopTrack()
            view.goToAlbumDetail(item.collectionId)
        }
    }

    override fun getAdapter(): ItunesTrackPagedAdapter {
        return trackPagedAdapter
    }

    override fun getSearchBarListener(): SearchView.OnQueryTextListener {
        return object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(searchText: String?): Boolean {
                searchText?.let {
                    if(it.isEmpty()){
                        trackPagedAdapter.submitList(null)
                        return true
                    }

                    trackLiveData?.removeObserver(trackObserver)

                    trackLiveData = view.getVMAndObserve(it, trackObserver)
                }?:run{
                    trackPagedAdapter.submitList(null)
                }

                return true
            }

            override fun onQueryTextChange(searchText: String?): Boolean { return false }

        }
    }

    override fun stopTrack() {
        itunesPlayer.stopTrack()
    }
}