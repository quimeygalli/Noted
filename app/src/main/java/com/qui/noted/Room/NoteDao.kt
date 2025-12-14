package com.qui.noted.Room

import androidx.room.*


// Dont touch this.

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes ORDER BY id DESC")
    suspend fun getAll(): List<NoteEntity>

    // upsert combines insert and update.
    // if an entry doesn't exist, it creates it. if it does, it updates it.
    @Upsert
    suspend fun upsert(note: NoteEntity)

    @Delete
    suspend fun delete(note: NoteEntity)
}