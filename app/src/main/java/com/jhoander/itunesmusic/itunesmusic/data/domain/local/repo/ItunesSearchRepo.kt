package com.jhoander.itunesmusic.itunesmusic.data.domain.local.repo

import com.jhoander.itunesmusic.itunesmusic.data.domain.entity.ItunesItem
import com.jhoander.itunesmusic.itunesmusic.data.domain.local.entity.ItunesSearchLocal
import com.jhoander.itunesmusic.itunesmusic.data.domain.mapper.ItunesItemLocalMapper
import io.realm.Realm
import io.realm.RealmList
import javax.inject.Inject

class ItunesSearchRepo @Inject constructor(
    private val itunesItemLocalMapper: ItunesItemLocalMapper
) {

    fun saveSearch(term: String, items: List<ItunesItem>){
        Realm.getDefaultInstance().use { realm ->
            realm.executeTransactionAsync {
                val item = ItunesSearchLocal(
                    term,
                    RealmList()
                )

                item.items.addAll(itunesItemLocalMapper.reverseMap(items))

                it.copyToRealmOrUpdate(item)
            }
        }
    }
}