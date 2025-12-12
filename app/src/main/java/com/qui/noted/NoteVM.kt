package com.qui.noted

import android.R
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qui.noted.Room.NoteEntity
import com.qui.noted.Room.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NoteVM(private val repository: NoteRepository) : ViewModel() {

    private val _notes = MutableStateFlow<List<NoteEntity>>(emptyList())

    val notes = _notes.asStateFlow()

    init {
        loadNotes()
    }

    private fun loadNotes() {
        viewModelScope.launch {
            _notes.value = repository.getNotes()
        }
    }

    fun createNote(): Int {
        val newNote = NoteEntity(
            id = null,
            title = "",
            body = ""
        )

        viewModelScope.launch {
            repository.upsert(newNote)
            loadNotes()
        }

        // This won't be the true DB-generated id yet — we'll fix that later.
        return newNote.id ?: -1
    }

    fun saveNote(note: NoteEntity) {
        viewModelScope.launch {
            repository.upsert(note)
            loadNotes()
        }
    }

    fun deleteNote(note: NoteEntity) {
        viewModelScope.launch {
            repository.deleteNote(note)
            loadNotes()
        }
    }
}
