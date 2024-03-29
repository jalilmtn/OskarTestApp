package com.example.oskartestapp.di

import android.app.Application
import androidx.room.Room
import com.example.oskartestapp.data.local.repo.AuthRepositoryImpl
import com.example.oskartestapp.data.source.MyDB
import com.example.oskartestapp.domain.repo.AuthRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Singleton
    @Named("test_db")
    fun provideNoteDatabase(app: Application): MyDB {
        return Room.inMemoryDatabaseBuilder(
            app,
            MyDB::class.java,
        ).build()
    }

    @Provides
    @Singleton
    @Named("test_repo")
    fun provideNoteRepository(db: MyDB): AuthRepo {
        return AuthRepositoryImpl(db.authDao)
    }

}