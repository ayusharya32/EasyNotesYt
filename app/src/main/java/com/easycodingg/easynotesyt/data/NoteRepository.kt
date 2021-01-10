package com.easycodingg.easynotesyt.data

import com.easycodingg.easynotesyt.data.entities.Note
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val noteDao: NoteDao
) {
    suspend fun insertNote(note: Note) = noteDao.insertNote(note)

    suspend fun deleteNote(note: Note) = noteDao.deleteNote(note)

    fun getAllNotesSortedByCreated() = noteDao.getAllNotesSortedByCreated()
}