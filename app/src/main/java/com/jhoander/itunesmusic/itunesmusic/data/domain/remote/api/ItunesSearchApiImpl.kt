package com.jhoander.itunesmusic.itunesmusic.data.domain.remote.api

import com.jhoander.itunesmusic.itunesmusic.data.domain.remote.entity.ItunesBaseRespRemote
import com.jhoander.itunesmusic.BuildConfig
import com.jhoander.itunesmusic.base.remote.ApiService
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton


@Singleton class ItunesSearchApiImpl @Inject constructor(): ItunesSearchApi {
    private val api = ApiService.build(
        ItunesSearchApi::class.java,
        BuildConfig.BASE_URL
    )

    override fun search(
        term: String,
        limit: Int,
        mediaType: String?
    ): Observable<ItunesBaseRespRemote> {
        return api.search(term, limit, mediaType)
    }

    override fun lookup(id: Long, entity: String?): Observable<ItunesBaseRespRemote> {
        return api.lookup(id, entity)
    }
}
 