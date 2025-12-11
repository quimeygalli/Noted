package com.qui.noted.Room

class NoteRepository(private val dao: NoteDao) {

    suspend fun getNotes() = dao.getAll()

    suspend fun addNote(title: String, body: String) {
        dao.upsert(NoteEntity(title = title, body = body))
    }

    suspend fun updateNote(note: NoteEntity) {
        dao.upsert(note = note)
    }

    suspend fun deleteNote(note: NoteEntity){
        dao.delete(note = note)
    }
}