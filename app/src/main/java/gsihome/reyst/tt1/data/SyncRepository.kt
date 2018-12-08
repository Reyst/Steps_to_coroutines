package gsihome.reyst.tt1.data

import gsihome.reyst.tt1.data.datasources.local.LocalDataSource2
import gsihome.reyst.tt1.data.datasources.remote.RemoteDataSourceSync
import gsihome.reyst.tt1.domain.entities.Album
import gsihome.reyst.tt1.domain.entities.Artist
import gsihome.reyst.tt1.domain.entities.Country
import gsihome.reyst.tt1.domain.repositories.CoroutinesRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class SyncRepository(
        private val remoteDataSource: RemoteDataSourceSync,
        private val localDataSource: LocalDataSource2
) : CoroutinesRepository {

    override suspend fun getCountries(): Deferred<List<Country>> =
            withContext(Dispatchers.IO) {
                async {
                    listOf(Country("Ukraine"), Country("France"), Country("Spain"))
                }
            }


    override suspend fun getTopArtistsByCountry(country: Country): Deferred<List<Artist>> {
        return withContext(Dispatchers.IO) {
            async {
                try {
                    remoteDataSource.getTopArtistsByCountry(country)
                            .let {
                                localDataSource.putArtistsByCountry(country, it)
                                it
                            }
                } catch (error: Throwable) {
                    localDataSource.getTopArtistsByCountry(country)
                }
            }
        }
    }

    override suspend fun getTopAlbumsByArtist(artist: Artist): Deferred<List<Album>> {
        return withContext(Dispatchers.IO) {
            async {
                remoteDataSource.getTopAlbumsByArtist(artist)
                        .let {
                            localDataSource.putAlbumsByArtist(artist, it)
                            it
                        }
            }
        }
    }
}