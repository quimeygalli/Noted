package com.qui.noted

import android.R
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qui.noted.Room.NoteEntity
import com.qui.noted.Room.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NoteVM(private val repository: NoteRepository): ViewModel() {
    private val _notes = MutableStateFlow<List<NoteEntity>>(emptyList())

    val notes = _notes.asStateFlow()

    init {
        launchNotes()
    }

    private fun launchNotes() {
        viewModelScope.launch {
            _notes.value = repository.getNotes()
        }
    }

    fun addNote(title: String, body: String) {
        viewModelScope.launch {
            repository.addNote(title, body)
            launchNotes()
        }
    }

    fun updateNote(note: NoteEntity) {
        viewModelScope.launch {
            repository.updateNote(note)
            launchNotes()
        }
    }
}