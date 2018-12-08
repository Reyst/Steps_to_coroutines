package gsihome.reyst.tt1.gui.artists

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.arellomobile.mvp.MvpActivity
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import gsihome.reyst.tt1.App
import gsihome.reyst.tt1.R
import gsihome.reyst.tt1.databinding.ActivityMainBinding
import gsihome.reyst.tt1.domain.entities.Artist
import gsihome.reyst.tt1.domain.entities.Country
import gsihome.reyst.tt1.gui.albums.AlbumsActivity
import gsihome.reyst.tt1.gui.artists.adapter.ArtistAdapter

@StateStrategyType(AddToEndSingleStrategy::class)
interface ArtistsView : MvpView {
    fun setData(items: List<Artist>)
    fun updateCountries(countries: List<Country>)
    fun showErrorMessage(message: String)
}

class MainActivity : MvpActivity(), ArtistsView {

    @InjectPresenter
    internal lateinit var presenter: ArtistPresenter

    @ProvidePresenter
    internal fun providePresenter(): ArtistPresenter {
        return ArtistPresenter(App.repository2)
    }

    private lateinit var binding: ActivityMainBinding

    private val adapter: ArtistAdapter = ArtistAdapter(this::openAlbumsScreen)

    private val countrySelectListener: AdapterView.OnItemSelectedListener by lazy {
        object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item = parent?.adapter?.getItem(position)
                if (item != null && item is Country) presenter.updateList(item)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.toolbar.setTitle(R.string.popular_artists)
        binding.rvMain.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvMain.adapter = adapter
    }

    private fun openAlbumsScreen(artist: Artist) = AlbumsActivity.start(this, artist)

    override fun updateCountries(countries: List<Country>) {
        val countryAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, countries)

        binding.countries.adapter = countryAdapter
        binding.countries.onItemSelectedListener = countrySelectListener
        binding.countries.setSelection(0)
    }

    override fun setData(items: List<Artist>) {
        adapter.updateItems(items)
    }

    override fun showErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}
