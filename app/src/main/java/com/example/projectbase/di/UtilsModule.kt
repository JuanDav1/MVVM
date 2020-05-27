package com.example.projectbase.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.projectbase.api.AplicationApi
import com.example.projectbase.db.AplicationDB
import com.example.projectbase.db.dao.DataBaseDao
import com.example.projectbase.models.Example
import com.example.projectbase.repositories.LocalReposiroty
import com.example.projectbase.repositories.RemoteRepository
import com.example.projectbase.viewmodels.ViewModelFactory
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class UtilsModule {

    private val baseUrl = "https://www.cheapshark.com/api/1.0/"

    @Volatile
    private var INSTANCE: AplicationDB? = null

    @Provides
    @Singleton
    fun getInstanceDB(context: Context): AplicationDB {
        synchronized(this) {
            var instance = INSTANCE

            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AplicationDB::class.java,
                    "aplication_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
            }
            return instance
        }
    }


    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val builder =
            GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return builder.setLenient().create()
    }

    @Provides
    @Singleton
    fun getApiCall(retrofit: Retrofit): AplicationApi {
        return retrofit.create(AplicationApi::class.java)
    }

    @Provides
    @Singleton
    fun provideExample() = Example("claseEjemplo")

    @Provides
    @Singleton
    fun getRequestHeader(): OkHttpClient {

        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .build()
            chain.proceed(request)
        }
            .connectTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .readTimeout(300, TimeUnit.SECONDS)

        return httpClient.build()
    }

    @Provides
    @Singleton
    fun getLocalRepository(dataBaseDao: DataBaseDao): LocalReposiroty {
        return LocalReposiroty(dataBaseDao)
    }

    @Provides
    @Singleton
    fun getViewModelFactory(remoteRepository: RemoteRepository, localReposiroty: LocalReposiroty): ViewModelProvider.Factory {
        return ViewModelFactory(remoteRepository, localReposiroty)
    }

    @Provides
    @Singleton
    fun getRemoteRepository(aplicationApiCall: AplicationApi): RemoteRepository {
        return RemoteRepository(aplicationApiCall)
    }

    @Singleton
    @Provides
    fun provideDao(aplicationDB: AplicationDB): DataBaseDao {
        return aplicationDB.dataBaseDao()
    }


}