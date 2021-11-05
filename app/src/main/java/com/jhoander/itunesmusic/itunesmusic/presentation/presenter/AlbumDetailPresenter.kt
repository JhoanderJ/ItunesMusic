package com.jhoander.itunesmusic.itunesmusic.presentation.presenter

import com.jhoander.itunesmusic.R
import com.jhoander.itunesmusic.base.presentation.ItemAdapterListener
import com.jhoander.itunesmusic.base.remote.UseCaseObserver
import com.jhoander.itunesmusic.itunesmusic.data.domain.entity.ItunesItem
import com.jhoander.itunesmusic.itunesmusic.data.domain.local.repo.ItunesItemRepo
import com.jhoander.itunesmusic.itunesmusic.data.domain.usecase.SearchByIdUseCase
import com.jhoander.itunesmusic.itunesmusic.presentation.adapter.ItunesTrackAdapter
import com.jhoander.itunesmusic.itunesmusic.presentation.contract.AlbumDetailContract
import javax.inject.Inject
import javax.inject.Singleton

@Singleton class AlbumDetailPresenter @Inject constructor(
    private val trackAdapter: ItunesTrackAdapter,
    private val itunesPlayer: ItunesPlayer,
    private val searchByIdUseCase: SearchByIdUseCase,
    private val itunesItemRepo: ItunesItemRepo
): AlbumDetailContract.AlbumDetailPresenter {
    private var itemPlaying: Long? = null

    private lateinit var view: AlbumDetailContract.AlbumDetailView

    override fun setView(view: AlbumDetailContract.AlbumDetailView) {
        this.view = view
    }

    override fun initAdapterListener() {
        trackAdapter.playListener = ItemAdapterListener { item, pos ->
            if(itemPlaying == item.trackId){
                itunesPlayer.stopTrack()
                itemPlaying = null
            }else {
                itemPlaying = item.trackId
                itunesPlayer.playTrack(item)
            }
        }
    }

    override fun findAlbumTracks(id: Long) {
        view.showProgress(true)

        searchByIdUseCase.execute(
            id, object: UseCaseObserver<List<ItunesItem>>(){
                override fun onNext(value: List<ItunesItem>) {
                    super.onNext(value)

                    val mutableTracks = value.toMutableList()

                    for (item in value){
                        if(item.wrapperType == "collection"){
                            view.showAlbumInfo(item)
                            mutableTracks.remove(item)
                            break
                        }
                    }

                    itunesItemRepo.save(mutableTracks)
                    view.showProgress(false)
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    view.showErrorMessage(R.string.ErrorLoading)
                    view.showProgress(false)
                }
            }
        )
    }

    override fun getAdapter(): ItunesTrackAdapter {
        return trackAdapter
    }

    override fun stopTrack() {
        itunesPlayer.stopTrack()
    }
}