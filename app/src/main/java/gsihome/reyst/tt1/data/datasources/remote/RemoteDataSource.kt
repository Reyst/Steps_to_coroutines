package gsihome.reyst.tt1.data.datasources.remote

import gsihome.reyst.tt1.domain.entities.Album
import gsihome.reyst.tt1.domain.entities.Artist
import gsihome.reyst.tt1.domain.entities.Country
import io.reactivex.Single

interface RemoteDataSource {
    fun getTopArtistsByCountry(country: Country): Single<List<Artist>>
    fun getTopAlbumsByArtist(artist: Artist): Single<List<Album>>
}