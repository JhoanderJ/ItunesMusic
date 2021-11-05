package com.jhoander.itunesmusic.itunesmusic.data.domain.local.entity

import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.LinkingObjects
import io.realm.annotations.PrimaryKey

open class ItunesItemLocal (
    var wrapperType : String = "",
    var kind : String = "",
    var artistId : Int = -1,
    var collectionId : Long = -1,
    @PrimaryKey var trackId : Long = -1,
    var artistName : String = "",
    var collectionName : String = "",
    var trackName : String = "",
    var collectionCensoredName : String = "",
    var trackCensoredName : String = "",
    var artistViewUrl : String = "",
    var collectionViewUrl : String = "",
    var trackViewUrl : String = "",
    var previewUrl : String? = null,
    var artworkUrl30 : String? = null,
    var artworkUrl60 : String? = null,
    var artworkUrl100 : String? = null,
    var collectionPrice : Double = 0.0,
    var trackPrice : Double = 0.0,
    var releaseDate : String = "",
    var collectionExplicitness : String = "",
    var trackExplicitness : String = "",
    var discCount : Int = -1,
    var discNumber : Int = -1,
    var trackCount : Int = -1,
    var trackNumber : Int = -1,
    var trackTimeMillis : Int? = null,
    var country : String = "",
    var currency : String = "",
    var primaryGenreName : String = "",
    var isStreamable : Boolean?  = null,
    var isPlaying: Boolean = false,
    var playedPercent: Int = 0,
    var playDuration: Int = 0,

    @LinkingObjects(ItunesSearchLocal.Keys.items)
    val searches: RealmResults<ItunesSearchLocal>? = null
): RealmObject(){
    fun cascadeDelete() {
        deleteFromRealm()
    }

    object Keys{
        const val searches = "searches"
        const val collectionId = "collectionId"
        const val wrapperType = "wrapperType"
    }
}