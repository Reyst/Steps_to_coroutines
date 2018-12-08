package gsihome.reyst.tt1.gui.albums

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpActivity
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import gsihome.reyst.tt1.App
import gsihome.reyst.tt1.R
import gsihome.reyst.tt1.databinding.ActivityAlbumsBinding
import gsihome.reyst.tt1.domain.entities.Album
import gsihome.reyst.tt1.domain.entities.Artist
import gsihome.reyst.tt1.gui.albums.adapter.AlbumsAdapter


@StateStrategyType(AddToEndSingleStrategy::class)
interface AlbumsView : MvpView {
    fun setData(items: List<Album>)
}

class AlbumsActivity : MvpActivity(), AlbumsView {

    @InjectPresenter
    internal lateinit var presenter: AlbumsPresenter

    @ProvidePresenter
    internal fun providePresenter(): AlbumsPresenter {
        return AlbumsPresenter(App.repository, intent.getParcelableExtra(KEY_ARTIST))
    }

    private val adapter: AlbumsAdapter by lazy {
        AlbumsAdapter(presenter.artist.image)
    }

    private lateinit var binding: ActivityAlbumsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_albums)
        initView()
    }

    private fun initView() {

        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        binding.toolbar.title = presenter.artist.name

        binding.rvMain.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvMain.adapter = adapter
    }

    override fun setData(items: List<Album>) {
        adapter.updateItems(items)
    }

    companion object {
        private const val KEY_ARTIST = "artist"

        fun start(parent: Activity, artist: Artist) {
            Intent(parent, AlbumsActivity::class.java)
                .apply {
                    putExtra(KEY_ARTIST, artist)
                    parent.startActivity(this)
                }
        }
    }
}
