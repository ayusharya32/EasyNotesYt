package com.easycodingg.easynotesyt.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.easycodingg.easynotesyt.data.entities.Note

@Database(
    entities = [Note::class],
    version = 1
)
abstract class NoteDatabase: RoomDatabase() {

    abstract fun getNoteDao(): NoteDao
}