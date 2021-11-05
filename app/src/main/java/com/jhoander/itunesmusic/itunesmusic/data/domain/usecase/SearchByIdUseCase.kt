package com.jhoander.itunesmusic.itunesmusic.data.domain.usecase

import com.jhoander.itunesmusic.base.remote.UseCaseParam
import com.jhoander.itunesmusic.itunesmusic.data.domain.entity.ItunesItem
import com.jhoander.itunesmusic.itunesmusic.data.domain.mapper.ItunesItemRemoteMapper
import com.jhoander.itunesmusic.itunesmusic.data.domain.remote.api.ItunesSearchApiImpl
import io.reactivex.Observable
import javax.inject.Inject

class SearchByIdUseCase @Inject constructor(
    private val itunesSearchApi: ItunesSearchApiImpl,
    private val itunesItemMapper: ItunesItemRemoteMapper
): UseCaseParam<List<ItunesItem>, Long>() {

    override fun createObservableUseCase(param: Long): Observable<List<ItunesItem>>? {
        return itunesSearchApi.lookup(param, "song").map {
                resp -> itunesItemMapper.map(resp.results)
        }
    }
}