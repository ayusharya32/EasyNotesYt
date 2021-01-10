package com.easycodingg.easynotesyt.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easycodingg.easynotesyt.data.NoteRepository
import com.easycodingg.easynotesyt.data.entities.Note
import kotlinx.coroutines.launch

class NoteViewModel @ViewModelInject constructor(
    private val repository: NoteRepository
): ViewModel() {

    val notes = repository.getAllNotesSortedByCreated()

    fun insertNote(note: Note) = viewModelScope.launch {
        repository.insertNote(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        repository.deleteNote(note)
    }
}