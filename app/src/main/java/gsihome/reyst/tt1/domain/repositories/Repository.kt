package gsihome.reyst.tt1.domain.repositories

import gsihome.reyst.tt1.domain.entities.Album
import gsihome.reyst.tt1.domain.entities.Artist
import gsihome.reyst.tt1.domain.entities.Country
import io.reactivex.Single

interface Repository {

    fun getCountries(): Single<List<Country>>

    fun getTopArtistsByCountry(country: Country): Single<List<Artist>>

    fun getTopAlbumsByArtist(artist: Artist): Single<List<Album>>
}