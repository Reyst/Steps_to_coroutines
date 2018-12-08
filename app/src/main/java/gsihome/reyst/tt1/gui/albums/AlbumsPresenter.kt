package gsihome.reyst.tt1.gui.albums

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import gsihome.reyst.tt1.domain.entities.Artist
import gsihome.reyst.tt1.domain.repositories.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

@InjectViewState
class AlbumsPresenter(
    private val repository: Repository,
    val artist: Artist
): MvpPresenter<AlbumsView>() {

    private val tasks = CompositeDisposable()

    override fun onFirstViewAttach() {
        repository.getTopAlbumsByArtist(artist)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { albums -> viewState.setData(albums) },
                { error -> Log.e("ERROR", error.message, error)}
            ).also { tasks.add(it) }
    }

    override fun onDestroy() {
        tasks.clear()
        super.onDestroy()
    }
}
