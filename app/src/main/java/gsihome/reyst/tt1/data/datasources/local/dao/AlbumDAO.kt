package gsihome.reyst.tt1.data.datasources.local.dao

import android.arch.persistence.room.*
import gsihome.reyst.tt1.data.datasources.local.entities.AlbumDB

@Dao
abstract class AlbumDAO {

    @Query("SELECT * FROM albums WHERE artistId = :id")
    abstract fun getByArtistId(id: String): List<AlbumDB>

    @Query("SELECT imageFileName FROM albums WHERE artistId = :artistId")
    abstract fun getImageFilenamesByArtist(artistId: String): List<String>

    @Insert
    abstract fun insert(albums: List<AlbumDB>)

    @Query("DELETE From albums where artistId = :id")
    abstract fun deleteByArtistId(id: String)

    @Transaction
    open fun updateAlbumsByArtist(artistId: String, albums: List<AlbumDB>) {
        deleteByArtistId(artistId)
        insert(albums)
    }
}