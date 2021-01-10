package com.easycodingg.easynotesyt.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.easycodingg.easynotesyt.data.entities.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM notes_table ORDER BY created desc")
    fun getAllNotesSortedByCreated(): LiveData<List<Note>>
}