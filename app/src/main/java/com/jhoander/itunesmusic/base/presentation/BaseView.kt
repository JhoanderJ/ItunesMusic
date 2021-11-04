package com.jhoander.itunesmusic.base.presentation

import android.content.Context
import androidx.annotation.StringRes
import com.jhoander.itunesmusic.App

interface BaseView {
    fun getContext(): Context?

    fun showProgress(show: Boolean){}

    fun showMessage(message: String?) {
        message?.let { App.showMessage(it, getContext()) }
    }

    fun showMessage(@StringRes message: Int) {
        App.showMessage(message, getContext())
    }

    fun showErrorMessage(message: String?) {
        showMessage(message)
    }

    fun showErrorMessage(@StringRes message: Int) {
        showMessage(message)
    }
}