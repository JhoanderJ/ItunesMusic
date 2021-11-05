package com.jhoander.itunesmusic.itunesmusic.data.domain.mapper

import com.jhoander.itunesmusic.itunesmusic.data.domain.local.entity.ItunesItemLocal
import com.jhoander.itunesmusic.base.data.Mapper
import com.jhoander.itunesmusic.itunesmusic.data.domain.entity.ItunesItem
import javax.inject.Inject
import javax.inject.Singleton


@Singleton class ItunesItemLocalMapper @Inject constructor(): Mapper<ItunesItemLocal, ItunesItem>() {
    override fun map(value: ItunesItemLocal): ItunesItem {
        return ItunesItem(
            wrapperType = value.wrapperType,
            kind = value.kind,
            artistId = value.artistId,
            collectionId = value.collectionId,
            trackId = value.trackId,
            artistName = value.artistName,
            collectionName = value.collectionName,
            trackName = value.trackName,
            collectionCensoredName = value.collectionCensoredName,
            trackCensoredName = value.trackCensoredName,
            artistViewUrl = value.artistViewUrl,
            collectionViewUrl = value.collectionViewUrl,
            trackViewUrl = value.trackViewUrl,
            previewUrl = value.previewUrl,
            artworkUrl30 = value.artworkUrl30,
            artworkUrl60 = value.artworkUrl60,
            artworkUrl100 = value.artworkUrl100,
            collectionPrice = value.collectionPrice,
            trackPrice = value.trackPrice,
            releaseDate = value.releaseDate,
            collectionExplicitness = value.collectionExplicitness,
            trackExplicitness = value.trackExplicitness,
            discCount = value.discCount,
            discNumber = value.discNumber,
            trackCount = value.trackCount,
            trackNumber = value.trackNumber,
            trackTimeMillis = value.trackTimeMillis,
            country = value.country,
            currency = value.currency,
            primaryGenreName = value.primaryGenreName,
            isStreamable = value.isStreamable,
            isPlaying = value.isPlaying,
            playedPercent = value.playedPercent,
            playDuration = value.playDuration
        )
    }

    override fun reverseMap(value: ItunesItem): ItunesItemLocal {
        return ItunesItemLocal(
            wrapperType = value.wrapperType,
            kind = value.kind,
            artistId = value.artistId,
            collectionId = value.collectionId,
            trackId = value.trackId,
            artistName = value.artistName,
            collectionName = value.collectionName,
            trackName = value.trackName,
            collectionCensoredName = value.collectionCensoredName,
            trackCensoredName = value.trackCensoredName,
            artistViewUrl = value.artistViewUrl,
            collectionViewUrl = value.collectionViewUrl,
            trackViewUrl = value.trackViewUrl,
            previewUrl = value.previewUrl,
            artworkUrl30 = value.artworkUrl30,
            artworkUrl60 = value.artworkUrl60,
            artworkUrl100 = value.artworkUrl100,
            collectionPrice = value.collectionPrice,
            trackPrice = value.trackPrice,
            releaseDate = value.releaseDate,
            collectionExplicitness = value.collectionExplicitness,
            trackExplicitness = value.trackExplicitness,
            discCount = value.discCount,
            discNumber = value.discNumber,
            trackCount = value.trackCount,
            trackNumber = value.trackNumber,
            trackTimeMillis = value.trackTimeMillis,
            country = value.country,
            currency = value.currency,
            primaryGenreName = value.primaryGenreName,
            isStreamable = value.isStreamable,
            isPlaying = value.isPlaying,
            playedPercent = value.playedPercent,
            playDuration = value.playDuration
        )
    }
}