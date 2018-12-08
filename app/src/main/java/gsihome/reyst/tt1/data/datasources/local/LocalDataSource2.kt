package gsihome.reyst.tt1.data.datasources.local

import gsihome.reyst.tt1.domain.entities.Album
import gsihome.reyst.tt1.domain.entities.Artist
import gsihome.reyst.tt1.domain.entities.Country
import io.reactivex.Completable
import io.reactivex.Single

interface LocalDataSource2 {

    fun getTopArtistsByCountry(country: Country): List<Artist>

    fun putArtistsByCountry(country: Country, artists: List<Artist>)

    fun getTopAlbumsByArtist(artist: Artist): List<Album>

    fun putAlbumsByArtist(artist: Artist, albums: List<Album>)
}