package com.jhoander.itunesmusic.itunesmusic.presentation.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.jhoander.itunesmusic.R
import com.jhoander.itunesmusic.itunesmusic.data.domain.entity.ItunesItem
import com.jhoander.itunesmusic.itunesmusic.presentation.contract.AlbumDetailContract
import com.jhoander.itunesmusic.itunesmusic.presentation.presenter.AlbumDetailPresenter
import com.jhoander.itunesmusic.itunesmusic.presentation.viewModel.ItunesTrackVM
import com.squareup.picasso.Picasso
import dagger.Component
import kotlinx.android.synthetic.main.activity_detail_album.*
import javax.inject.Singleton

class AlbumDetailActivity : AppCompatActivity(), AlbumDetailContract.AlbumDetailView {
    private lateinit var presenter: AlbumDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_album)

        if(!intent.hasExtra(ID_KEY)){
            finish()
        }

        val comp: Comp = DaggerAlbumDetailActivity_Comp.create()
        presenter = comp.getPresenter()
        ivBack.setOnClickListener { onBackPressed() }

        presenter.setView(this)
        presenter.initAdapterListener()
        val adapter = presenter.getAdapter()
        albumDetTracksRv.adapter = adapter
        albumDetTracksRv.itemAnimator = null
        val id = intent.getLongExtra(ID_KEY, -1)
        presenter.findAlbumTracks(id)
        ivBack.setOnClickListener { onBackPressed() }
        val trackLiveData = ViewModelProviders
            .of(this@AlbumDetailActivity)
            .get(ItunesTrackVM::class.java)
            .getAlbumTracksLiveData(id)

        trackLiveData.observe(this@AlbumDetailActivity, { adapter.items = it })
    }

    override fun getContext(): Context {
        return this
    }

    override fun showProgress(show: Boolean) {
        super.showProgress(show)
        albumDetProgress.visibility = if(show) View.VISIBLE else View.GONE
        albumDetTracksRv.visibility = if(!show) View.VISIBLE else View.GONE
    }

    override fun showAlbumInfo(album: ItunesItem) {
        album.artworkUrl100?.let {
            Picasso.get().load(it).into(albumDetThumb)
        }

        albumDetTitle.text = album.collectionName
        albumDetArtist.text = album.artistName
    }

    companion object{
        private val ID_KEY = "idkey"

        fun newInstance(context: Context, id: Long): Intent{
            val intent = Intent(context, AlbumDetailActivity::class.java)
            intent.putExtra(ID_KEY, id)

            return intent
        }
    }

    @Singleton @Component interface Comp{
        fun getPresenter(): AlbumDetailPresenter
    }
}
