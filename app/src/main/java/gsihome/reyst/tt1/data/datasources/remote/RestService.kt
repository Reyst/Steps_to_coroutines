package gsihome.reyst.tt1.data.datasources.remote

import gsihome.reyst.tt1.data.datasources.remote.dto.AlbumsResponse
import gsihome.reyst.tt1.data.datasources.remote.dto.ArtistsResponse
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RestService {

    @GET("/2.0/")
    fun getArtistsByCountry(
        @Query("method") method: String,
        @Query("country") country: String,
        @Query("api_key") apiKey: String,
        @Query("format") format: String
    ): Single<ArtistsResponse>

    @GET("/2.0/")
    fun getAlbumsByArtist(
        @Query("method") method: String,
        @Query("artist") artist: String,
        @Query("api_key") apiKey: String,
        @Query("format") format: String
    ): Single<AlbumsResponse>

    @GET("/2.0/")
    fun getArtistsByCountrySync(
        @Query("method") method: String,
        @Query("country") country: String,
        @Query("api_key") apiKey: String,
        @Query("format") format: String
    ): Call<ArtistsResponse>

    @GET("/2.0/")
    fun getAlbumsByArtistSync(
        @Query("method") method: String,
        @Query("artist") artist: String,
        @Query("api_key") apiKey: String,
        @Query("format") format: String
    ): Call<AlbumsResponse>
}