package gsihome.reyst.tt1

import android.app.Application
import android.arch.persistence.room.Room
import com.squareup.picasso.Picasso
import gsihome.reyst.tt1.data.DefaultRepository
import gsihome.reyst.tt1.data.SyncRepository
import gsihome.reyst.tt1.data.datasources.local.*
import gsihome.reyst.tt1.data.datasources.remote.*
import gsihome.reyst.tt1.domain.repositories.Repository
import gsihome.reyst.tt1.domain.repositories.CoroutinesRepository
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class App : Application() {

    private val restService: RestService by lazy {
        Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
                .create(RestService::class.java)
    }

    private val remoteDataSourceSync: RemoteDataSourceSync by lazy { RestDataSourceSync(restService, Picasso.get()) }

    private val localDataSource2: LocalDataSource2 by lazy {

        val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "database"
        ).build()
        RoomDataSourceSync(db, applicationContext.filesDir)

    }

    private val remoteDataSource: RemoteDataSource by lazy { RestDataSource(restService, Picasso.get()) }

    private val localDataSource: LocalDataSource by lazy {

        val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "database"
        ).build()
        RoomDataSource(db, applicationContext.filesDir)

    }

    override fun onCreate() {
        super.onCreate()
        repository2 = SyncRepository(remoteDataSourceSync, localDataSource2)
        repository = DefaultRepository(remoteDataSource, localDataSource)
    }

    companion object {
        private const val BASE_URL = "http://ws.audioscrobbler.com/"

        lateinit var repository2: CoroutinesRepository
            private set

        lateinit var repository: Repository
            private set

    }
}