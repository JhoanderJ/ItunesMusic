package com.jhoander.itunesmusic.base.remote

import com.jhoander.itunesmusic.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiService {
    @JvmOverloads fun <T> build(
        serviceClass: Class<T>,
        urlBase: String,
        client: OkHttpClient.Builder = OkHttpClient.Builder()
    ): T {
        if (BuildConfig.DEBUG) {
            activateDebug(client)
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(urlBase)
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return retrofit.create(serviceClass)
    }


    private fun activateDebug(client: OkHttpClient.Builder) {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        client.addInterceptor(logging)
    }
}