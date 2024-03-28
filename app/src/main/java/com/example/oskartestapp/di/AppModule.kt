package com.example.oskartestapp.di

import android.app.Application
import androidx.room.Room
import com.example.oskartestapp.common.Constants
import com.example.oskartestapp.data.local.repo.AuthRepositoryImpl
import com.example.oskartestapp.data.remote.AuthenticationInterceptor
import com.example.oskartestapp.data.remote.OryApi
import com.example.oskartestapp.data.remote.repo.OryRepositoryImpl
import com.example.oskartestapp.data.source.MyDB
import com.example.oskartestapp.domain.repo.AuthRepo
import com.example.oskartestapp.domain.repo.OryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDB(application: Application) =
        Room.databaseBuilder(
            application,
            MyDB::class.java,
            MyDB.DB_NAME
        ).build()


    @Provides
    @Singleton
    fun provideAuthRepo(myDB: MyDB): AuthRepo {
        return AuthRepositoryImpl(myDB.authDao)
    }

    @Provides
    @Singleton
    fun getInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
    }

    @Provides
    @Singleton
    fun client(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authenticationInterceptor: AuthenticationInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(authenticationInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(okHttpClient: OkHttpClient): OryApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(OryApi::class.java)
    }


    @Provides
    @Singleton
    fun provideOryRepo(api: OryApi): OryRepository {
        return OryRepositoryImpl(api)
    }

}