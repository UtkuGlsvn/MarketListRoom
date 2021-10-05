package com.utkuglsvn.roombasic.core.di

import android.content.Context
import androidx.room.Room
import com.utkuglsvn.roombasic.core.local.room.MyListDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

class AppModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext app: Context) =
        Room.databaseBuilder(app, MyListDatabase::class.java, MyListDatabase.DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideMyListDao(db:MyListDatabase) = db.getMyList()
}