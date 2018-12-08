package gsihome.reyst.tt1.domain.repositories

import gsihome.reyst.tt1.domain.entities.Album
import gsihome.reyst.tt1.domain.entities.Artist
import gsihome.reyst.tt1.domain.entities.Country
import kotlinx.coroutines.Deferred

interface CoroutinesRepository {

    suspend fun getCountries(): Deferred<List<Country>>

    suspend fun getTopArtistsByCountry(country: Country): Deferred<List<Artist>>

    suspend fun getTopAlbumsByArtist(artist: Artist): Deferred<List<Album>>
}