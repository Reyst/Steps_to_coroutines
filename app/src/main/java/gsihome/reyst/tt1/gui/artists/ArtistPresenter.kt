package gsihome.reyst.tt1.gui.artists

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import gsihome.reyst.tt1.domain.entities.Country
import gsihome.reyst.tt1.domain.repositories.CoroutinesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@InjectViewState
class ArtistPresenter(
        private val repository: CoroutinesRepository
) : MvpPresenter<ArtistsView>() {

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    override fun onFirstViewAttach() {
        uiScope.launch {
            val countries = repository.getCountries().await()
            viewState.updateCountries(countries)
        }
    }

    fun updateList(country: Country) {
        uiScope.launch {
            try {
                val artists = repository.getTopArtistsByCountry(country).await()
                viewState.setData(artists.sorted())
            } catch (error: Throwable) {
                viewState.showErrorMessage(error.localizedMessage ?: error.toString())
            }
        }
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}
