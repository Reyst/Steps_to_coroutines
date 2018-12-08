package gsihome.reyst.tt1.data.datasources.local

import gsihome.reyst.tt1.domain.entities.Album
import gsihome.reyst.tt1.domain.entities.Artist
import gsihome.reyst.tt1.domain.entities.Country
import io.reactivex.Completable
import io.reactivex.Single

interface LocalDataSource {

    fun getTopArtistsByCountry(country: Country): Single<List<Artist>>

    fun putArtistsByCountry(country: Country, artists: List<Artist>): Completable

    fun getTopAlbumsByArtist(artist: Artist): Single<List<Album>>

    fun putAlbumsByArtist(artist: Artist, albums: List<Album>): Completable
}