package com.jhoander.itunesmusic.itunesmusic.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.jhoander.itunesmusic.base.remote.UseCaseObserver
import com.jhoander.itunesmusic.itunesmusic.data.domain.entity.ItunesItem
import com.jhoander.itunesmusic.itunesmusic.data.domain.entity.SearchByTermParams
import com.jhoander.itunesmusic.itunesmusic.data.domain.local.repo.ItunesItemRepo
import com.jhoander.itunesmusic.itunesmusic.data.domain.local.repo.ItunesSearchRepo
import com.jhoander.itunesmusic.itunesmusic.data.domain.mapper.ItunesItemLocalMapper
import com.jhoander.itunesmusic.itunesmusic.data.domain.usecase.SearchByTermUseCase
import com.jhoander.itunesmusic.itunesmusic.data.domain.utils.ItunesConstants.SEARCH_DEF_LIMIT
import com.jhoander.itunesmusic.itunesmusic.presentation.contract.ItunesTrackView
import com.zhuinden.monarchy.Monarchy
import dagger.Component
import java.util.*
import javax.inject.Singleton


class ItunesTrackVM: ViewModel() {
    private val pageSize = SEARCH_DEF_LIMIT
    private val monarchy = Monarchy.Builder().build()
    private val comp: Comp = DaggerItunesTrackVM_Comp.create()
    private val itunesItemLocalMapper = comp.getItunesItemLocalMapper()

    private lateinit var livePagedListBuilder: LivePagedListBuilder<Int, ItunesItem>

    private var view: ItunesTrackView? = null

    fun setView(view: ItunesTrackView): ItunesTrackVM{
        this.view = view
        return this
    }

    fun getAlbumTracksLiveData(albumId: Long): LiveData<List<ItunesItem>> = Transformations.map(
        comp.getItunesItemRepo().getAlbumLiveData(albumId)
    ) { input -> comp.getItunesItemLocalMapper().map(input) }

    fun setTerm(term: String): LiveData<PagedList<ItunesItem>>{
        val realmDataSourceFactory = comp.getItunesItemRepo().getMonarchySource(monarchy, term)
        val dataSourceFactory = realmDataSourceFactory.map {
            itunesItemLocalMapper.map(it)
        }

        livePagedListBuilder = LivePagedListBuilder(dataSourceFactory, pageSize)
        livePagedListBuilder.setBoundaryCallback(object: PagedList.BoundaryCallback<ItunesItem>(){
            override fun onItemAtEndLoaded(itemAtEnd: ItunesItem) {
                super.onItemAtEndLoaded(itemAtEnd)
                comp.getItunesSearchUseCase().execute(
                    SearchByTermParams(
                        term,
                        (comp.getItunesItemRepo().countByTerm(term) + pageSize).toInt()
                    ), object: UseCaseObserver<List<ItunesItem>>(){
                        override fun onNext(value: List<ItunesItem>) {
                            super.onNext(value)
                            comp.getItunesSearchRepo().saveSearch(term, value)
                        }
                    })
            }

            override fun onZeroItemsLoaded() {
                super.onZeroItemsLoaded()

                comp.getItunesSearchUseCase().execute(
                    SearchByTermParams(
                        term,
                        pageSize
                    ), object: UseCaseObserver<List<ItunesItem>>(){
                        override fun onNext(value: List<ItunesItem>) {
                            super.onNext(value)
                            view?.onSearch(value.isEmpty())
                            comp.getItunesSearchRepo().saveSearch(term, value)
                        }

                        override fun onError(e: Throwable) {
                            super.onError(e)
                            view?.onSearch(true)
                        }
                    }
                )
            }
        })

        return monarchy.findAllPagedWithChanges(realmDataSourceFactory, livePagedListBuilder)
    }

    @Singleton @Component interface Comp{
        fun getItunesSearchRepo(): ItunesSearchRepo
        fun getItunesItemRepo(): ItunesItemRepo
        fun getItunesSearchUseCase(): SearchByTermUseCase
        fun getItunesItemLocalMapper(): ItunesItemLocalMapper
    }
}