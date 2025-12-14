package com.qui.noted.Room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.qui.noted.NoteVM

// Credits to @Ali in stackOverflow for helping with this.

class NoteVMFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(NoteVM::class.java)) {
            return NoteVM(repository) as T
        }

        throw IllegalArgumentException("This viewmodel class is unknown")
    }
}
