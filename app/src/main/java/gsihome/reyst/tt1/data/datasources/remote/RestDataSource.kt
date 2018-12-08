package gsihome.reyst.tt1.data.datasources.remote

import android.graphics.Bitmap
import com.squareup.picasso.Picasso
import gsihome.reyst.tt1.data.datasources.remote.dto.AlbumDto
import gsihome.reyst.tt1.data.datasources.remote.dto.ArtistDto
import gsihome.reyst.tt1.data.datasources.remote.dto.ImageDto
import gsihome.reyst.tt1.domain.entities.Album
import gsihome.reyst.tt1.domain.entities.Artist
import gsihome.reyst.tt1.domain.entities.Country
import io.reactivex.Single

class RestDataSource(
    private val service: RestService,
    private val picasso: Picasso
) : RemoteDataSource {

    override fun getTopArtistsByCountry(country: Country): Single<List<Artist>> {
        return service.getArtistsByCountry(
            METHOD_GET_TOP_ARTISTS,
            country.name.toLowerCase(),
            API_KEY,
            FORMAT
        )
            .map { response -> response.topartists.artists }
            .map { artists: List<ArtistDto> ->
                return@map artists.map { artistDto -> convertArtistDtoToDomain(artistDto) }
            }
    }

    private fun convertArtistDtoToDomain(artistDto: ArtistDto): Artist {
        return Artist(
            id = artistDto.mbid,
            name = artistDto.name,
            count = artistDto.listeners.toInt(),
            image = getBitmapBySize(artistDto.image, "large")
        )
    }

    override fun getTopAlbumsByArtist(artist: Artist): Single<List<Album>> {
        return service.getAlbumsByArtist(
            METHOD_GET_ALBUMS,
            artist.name.toLowerCase(),
            API_KEY,
            FORMAT
        )
            .map { response -> response.topalbums.albums }
            .map { albums: List<AlbumDto> ->
                return@map albums.map { albumDto -> convertAlbumDtoToDomain(albumDto) }
            }
    }

    private fun convertAlbumDtoToDomain(albumDto: AlbumDto): Album {
        return Album(
            id = albumDto.mbid ?: albumDto.url.hashCode().toString(),
            name = albumDto.name,
            count = albumDto.playcount,
            image = getBitmapBySize(albumDto.image, "large"),
            artistId = albumDto.artist.mbid
        )
    }


    private fun getBitmapBySize(images: List<ImageDto>, size: String): Bitmap? {
        return images.firstOrNull { imageDto -> imageDto.size == size }
            ?.url
            ?.let { if (it.isNotBlank()) picasso.load(it).get() else null }
    }

    companion object {
        private const val API_KEY: String = "e81f61890b7ff8633ca024d0faa449e7"
        private const val FORMAT = "json"

        private const val METHOD_GET_TOP_ARTISTS = "geo.gettopartists"
        private const val METHOD_GET_ALBUMS = "artist.gettopalbums"

    }
}
