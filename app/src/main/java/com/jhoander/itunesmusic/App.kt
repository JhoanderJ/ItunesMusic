package com.jhoander.itunesmusic

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import com.zhuinden.monarchy.Monarchy
import io.realm.RealmConfiguration

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        app = this

        Monarchy.init(this)
        Monarchy.setDefaultConfiguration(RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build())
    }

    companion object {
        lateinit var app: Application private set

        @JvmOverloads fun showMessage(message: String, context: Context? = null){
            Toast.makeText(context ?: app, message, Toast.LENGTH_LONG).show()
        }

        @JvmOverloads fun showMessage(@StringRes message: Int, context: Context? = null) {
            Toast.makeText(context ?: app, message, Toast.LENGTH_LONG).show()
        }
    }
}