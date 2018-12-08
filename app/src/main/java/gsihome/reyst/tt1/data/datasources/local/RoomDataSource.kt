package gsihome.reyst.tt1.data.datasources.local

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import gsihome.reyst.tt1.data.datasources.local.entities.AlbumDB
import gsihome.reyst.tt1.data.datasources.local.entities.ArtistDB
import gsihome.reyst.tt1.domain.entities.Album
import gsihome.reyst.tt1.domain.entities.Artist
import gsihome.reyst.tt1.domain.entities.Country
import io.reactivex.Completable
import io.reactivex.Single
import java.io.File
import java.io.IOException

class RoomDataSource(
    private val db: AppDatabase,
    fileDir: File
) : LocalDataSource {

    private val imageFolder: File = File(fileDir, "images")

    init {
        if (!imageFolder.exists() || !imageFolder.isDirectory)
            imageFolder.mkdirs()
    }


    override fun getTopArtistsByCountry(country: Country): Single<List<Artist>> {

        return Single.fromCallable { db.artistDao().getByCountryId(country.name.toLowerCase()) }
            .map { list: List<ArtistDB> ->
                list.map {
                    Artist(
                        id = it.id,
                        name = it.name,
                        count = it.count,
                        image = getBitmapFromDisk(it.imageFileName)
                    )
                }
            }
    }

    override fun getTopAlbumsByArtist(artist: Artist): Single<List<Album>> {
        return Single.fromCallable { db.albumDao().getByArtistId(artist.id) }
            .map { list: List<AlbumDB> ->
                list.map {
                    Album(
                        id = it.id,
                        name = it.name,
                        count = it.count,
                        image = getBitmapFromDisk(it.imageFileName),
                        artistId = it.artistId
                    )
                }
            }
    }

    private fun getBitmapFromDisk(imageFileName: String): Bitmap? {
        return if (imageFileName.isNotBlank()) BitmapFactory.decodeFile(File(imageFolder, imageFileName).path)
        else null
    }

    override fun putArtistsByCountry(country: Country, artists: List<Artist>): Completable {

        return Completable.fromAction {

            val artistDao = db.artistDao()

            artistDao.getImageFilenamesByCountry(country.name.toLowerCase())
                .forEach { deleteImage(it) }

            val artistsDB = artists.map {
                ArtistDB(
                    it.id,
                    country.name.toLowerCase(),
                    it.name,
                    it.count,
                    it.image
                )
            }

            artistDao.updateArtistsByCountry(country.name.toLowerCase(), artistsDB)

            artistsDB.filter { it.imageFileName.isNotBlank() }
                .forEach { saveImage(it.image, it.imageFileName) }
        }
    }

    override fun putAlbumsByArtist(artist: Artist, albums: List<Album>): Completable {
        return Completable.fromAction {

            val albumDao = db.albumDao()

            albumDao.getImageFilenamesByArtist(artist.id)
                .forEach { deleteImage(it) }

            val albumsDB = albums.map {
                AlbumDB(
                    it.id,
                    artist.id,
                    it.name,
                    it.count,
                    it.image
                )
            }

            albumDao.updateAlbumsByArtist(artist.id, albumsDB)

            albumsDB.filter { it.imageFileName.isNotBlank() }
                .forEach { saveImage(it.image, it.imageFileName) }
        }
    }

    private fun deleteImage(fileName: String) {
        try {
            File(imageFolder, fileName).delete()
        } catch (error: IOException) {
            Log.e("ERROR", error.message, error)
        }
    }

    private fun saveImage(image: Bitmap?, imageFileName: String) {
        image?.compress(
            Bitmap.CompressFormat.PNG,
            0,
            File(imageFolder, imageFileName).let {
                if (!it.exists()) it.createNewFile()
                it.outputStream()
            }
        )
    }

}