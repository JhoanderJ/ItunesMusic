package com.jhoander.itunesmusic.itunesmusic.data.domain.usecase

import com.jhoander.itunesmusic.base.remote.UseCaseParam
import com.jhoander.itunesmusic.itunesmusic.data.domain.entity.ItunesItem
import com.jhoander.itunesmusic.itunesmusic.data.domain.entity.SearchByTermParams
import com.jhoander.itunesmusic.itunesmusic.data.domain.mapper.ItunesItemRemoteMapper
import com.jhoander.itunesmusic.itunesmusic.data.domain.remote.api.ItunesSearchApiImpl
import io.reactivex.Observable
import javax.inject.Inject


class SearchByTermUseCase @Inject constructor(
    private val itunesSearchApi: ItunesSearchApiImpl,
    private val itunesItemMapper: ItunesItemRemoteMapper
): UseCaseParam<List<ItunesItem>, SearchByTermParams>() {

    override fun createObservableUseCase(param: SearchByTermParams): Observable<List<ItunesItem>>? {
        return itunesSearchApi.search(param.term, param.limit, param.mediaType).map {
                resp -> itunesItemMapper.map(resp.results)
        }
    }
}