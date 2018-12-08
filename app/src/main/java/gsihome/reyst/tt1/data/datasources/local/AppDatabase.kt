package gsihome.reyst.tt1.data.datasources.local

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import gsihome.reyst.tt1.data.datasources.local.dao.AlbumDAO
import gsihome.reyst.tt1.data.datasources.local.dao.ArtistDAO
import gsihome.reyst.tt1.data.datasources.local.entities.AlbumDB
import gsihome.reyst.tt1.data.datasources.local.entities.ArtistDB


@Database(entities = [ArtistDB::class, AlbumDB::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun artistDao(): ArtistDAO

    abstract fun albumDao(): AlbumDAO
}