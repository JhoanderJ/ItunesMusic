package com.jhoander.itunesmusic.base.remote

import io.reactivex.observers.DisposableObserver

abstract class UseCaseObserver <T> : DisposableObserver<T>() {
    override fun onNext(value: T) {}

    override fun onError(e: Throwable) {
        e.printStackTrace()
    }

    override fun onComplete() {}
}