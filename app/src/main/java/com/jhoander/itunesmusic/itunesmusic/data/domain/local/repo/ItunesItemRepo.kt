package com.jhoander.itunesmusic.itunesmusic.data.domain.local.repo

import com.jhoander.itunesmusic.base.presentation.LiveDataRepository
import com.jhoander.itunesmusic.itunesmusic.data.domain.entity.ItunesItem
import com.jhoander.itunesmusic.itunesmusic.data.domain.local.entity.ItunesItemLocal
import com.jhoander.itunesmusic.itunesmusic.data.domain.local.entity.ItunesSearchLocal
import com.jhoander.itunesmusic.itunesmusic.data.domain.mapper.ItunesItemLocalMapper
import com.zhuinden.monarchy.Monarchy
import io.realm.Realm
import io.realm.RealmQuery
import javax.inject.Inject


class ItunesItemRepo @Inject constructor(
    private val itunesItemLocalMapper: ItunesItemLocalMapper
) {
    private val realmClass get() = ItunesItemLocal::class.java

    private fun getTermQuery(realm: Realm, term: String): RealmQuery<ItunesItemLocal> {
        return realm
            .where(realmClass)
            .equalTo(
                "${ItunesItemLocal.Keys.searches}.${ItunesSearchLocal.Keys.term}",
                term
            )
    }

    fun save(items: List<ItunesItem>){
        Realm.getDefaultInstance().use { realm ->
            realm.executeTransactionAsync {
                it.copyToRealmOrUpdate(itunesItemLocalMapper.reverseMap(items))
            }
        }
    }

    fun save(item: ItunesItem){
        Realm.getDefaultInstance().use { realm ->
            realm.executeTransactionAsync {
                it.copyToRealmOrUpdate(itunesItemLocalMapper.reverseMap(item))
            }
        }
    }

    fun getMonarchySource(monarchy: Monarchy, term: String): Monarchy.RealmDataSourceFactory<ItunesItemLocal>{
        return monarchy.createDataSourceFactory {
            getTermQuery(it, term)
        }
    }

    fun countByTerm(term: String): Long{
        return  getTermQuery(Realm.getDefaultInstance(), term).count()
    }

    fun getAlbumLiveData(albumId: Long) = LiveDataRepository(
        Realm.getDefaultInstance()
                .where(realmClass)
                .equalTo(ItunesItemLocal.Keys.collectionId, albumId)
                .equalTo(ItunesItemLocal.Keys.wrapperType, "track")
                .findAllAsync()
    )
}