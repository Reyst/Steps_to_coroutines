package gsihome.reyst.tt1.data

import android.util.Log
import gsihome.reyst.tt1.data.datasources.local.LocalDataSource
import gsihome.reyst.tt1.data.datasources.remote.RemoteDataSource
import gsihome.reyst.tt1.domain.entities.Album
import gsihome.reyst.tt1.domain.entities.Artist
import gsihome.reyst.tt1.domain.entities.Country
import gsihome.reyst.tt1.domain.repositories.Repository
import io.reactivex.Single

class DefaultRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : Repository {

    override fun getCountries(): Single<List<Country>> {
        return Single.just(
            listOf(
                Country("Ukraine"),
                Country("France"),
                Country("Spain")
            )
        )
    }

    override fun getTopArtistsByCountry(country: Country): Single<List<Artist>> {
        return remoteDataSource.getTopArtistsByCountry(country)
            .flatMap { artists ->
                localDataSource
                    .putArtistsByCountry(country, artists)
                    .andThen(Single.just(artists))
            }
            .onErrorResumeNext { error ->
                Log.e("ERROR", error.message, error)
                localDataSource.getTopArtistsByCountry(country)
            }
    }

    override fun getTopAlbumsByArtist(artist: Artist): Single<List<Album>> {
        return remoteDataSource.getTopAlbumsByArtist(artist)
            .flatMap { albums ->
                localDataSource.putAlbumsByArtist(artist, albums)
                    .andThen(Single.just(albums))
            }
            .onErrorResumeNext { error ->
                Log.e("ERROR", error.message, error)
                localDataSource.getTopAlbumsByArtist(artist)
            }
    }
}