package gsihome.reyst.tt1.data.datasources.local.dao

import android.arch.persistence.room.*
import gsihome.reyst.tt1.data.datasources.local.entities.ArtistDB


@Dao
abstract class ArtistDAO {

    @Query("SELECT * FROM artists WHERE country = :country")
    abstract fun getByCountryId(country: String): List<ArtistDB>

    @Query("SELECT imageFileName FROM artists WHERE country = :country")
    abstract fun getImageFilenamesByCountry(country: String): List<String>

    @Insert
    abstract fun insert(artists: List<ArtistDB>)

    @Query("DELETE From artists where country = :country")
    abstract fun deleteByCountryId(country: String)

    @Transaction
    open fun updateArtistsByCountry(country: String, artists: List<ArtistDB>) {
        deleteByCountryId(country)
        insert(artists)
    }
}