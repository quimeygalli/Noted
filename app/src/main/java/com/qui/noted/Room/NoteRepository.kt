package com.qui.noted.Room

class NoteRepository(private val dao: NoteDao) {

    suspend fun getNotes() = dao.getAll()

    suspend fun upsert(note: NoteEntity) {
        dao.upsert(note)
    }

    suspend fun deleteNote(note: NoteEntity){
        dao.delete(note = note)
    }
}