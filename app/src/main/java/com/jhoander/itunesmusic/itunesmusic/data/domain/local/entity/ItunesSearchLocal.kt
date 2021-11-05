package com.jhoander.itunesmusic.itunesmusic.data.domain.local.entity

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ItunesSearchLocal(
    @PrimaryKey var term: String = "",
    var items: RealmList<ItunesItemLocal> = RealmList()
): RealmObject(){
    object Keys{
        const val term = "term"
        const val items = "items"
    }
}