package com.easycodingg.easynotesyt.di

import android.content.Context
import androidx.room.Room
import com.easycodingg.easynotesyt.data.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNoteDatabase(
        @ApplicationContext context: Context
    ) =
        Room.databaseBuilder(context, NoteDatabase::class.java, "noteDB").build()

    @Singleton
    @Provides
    fun provideNoteDao(db: NoteDatabase) = db.getNoteDao()
}