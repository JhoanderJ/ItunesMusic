package com.jhoander.itunesmusic.itunesmusic.data.domain.remote.api

import com.jhoander.itunesmusic.itunesmusic.data.domain.remote.entity.ItunesBaseRespRemote
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ItunesSearchApi {
    @GET("search") fun search(
        @Query("term") term: String,
        @Query("limit") limit: Int,
        @Query("mediaType") mediaType: String?
    ): Observable<ItunesBaseRespRemote>

    @GET("lookup") fun lookup(
        @Query("id") id: Long,
        @Query("entity") entity: String?
    ): Observable<ItunesBaseRespRemote>
}