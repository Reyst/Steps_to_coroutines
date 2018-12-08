package gsihome.reyst.tt1.data.datasources.remote

import gsihome.reyst.tt1.domain.entities.Album
import gsihome.reyst.tt1.domain.entities.Artist
import gsihome.reyst.tt1.domain.entities.Country

interface RemoteDataSourceSync {
    fun getTopArtistsByCountry(country: Country): List<Artist>
    fun getTopAlbumsByArtist(artist: Artist): List<Album>
}