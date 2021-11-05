package com.jhoander.itunesmusic.itunesmusic.presentation.activities

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import com.jhoander.itunesmusic.R
import com.jhoander.itunesmusic.itunesmusic.data.domain.entity.ItunesItem
import com.jhoander.itunesmusic.itunesmusic.presentation.contract.ItunesTrackView
import com.jhoander.itunesmusic.itunesmusic.presentation.contract.ListContract
import com.jhoander.itunesmusic.itunesmusic.presentation.presenter.ListPresenter
import com.jhoander.itunesmusic.itunesmusic.presentation.viewModel.ItunesTrackVM
import dagger.Component
import kotlinx.android.synthetic.main.activity_list.*
import javax.inject.Singleton

class ListActivity : AppCompatActivity(), ListContract.ListView , ItunesTrackView {

    private lateinit var presenter: ListContract.ListPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        val comp : Comp = DaggerListActivity_Comp.create()
        presenter = comp.getPresenter()

        presenter.setView(this)
        presenter.initAdapterListener()
        mainTracksRv.adapter = presenter.getAdapter()
        mainTracksRv.itemAnimator = null
        mainSearchView.setOnQueryTextListener(presenter.getSearchBarListener())

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.stopTrack()
    }

    override fun getVMAndObserve(
        term: String,
        observer: Observer<PagedList<ItunesItem>>
    ): LiveData<PagedList<ItunesItem>> {
        val trackLiveData = ViewModelProviders
            .of(this@ListActivity)
            .get(ItunesTrackVM::class.java)
            .setView(this)
            .setTerm(term)

        trackLiveData.observe(this@ListActivity, observer)
        hideKeyboard(this)
        return trackLiveData
    }

    override fun goToAlbumDetail(id: Long) {
        startActivity(AlbumDetailActivity.newInstance(this, id))
    }


    fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view: View? = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun getContext(): Context {
        return this
    }

    override fun onSearch(notFound: Boolean) {
        mainNotFoundTV.visibility = if(notFound) View.VISIBLE else View.GONE
        mainTracksRv.visibility = if(!notFound) View.VISIBLE else View.GONE
        presenter.stopTrack()
    }

    @Singleton @Component interface Comp{
        fun getPresenter(): ListPresenter
    }
}