package com.example.todoapp

class NoteRepository(private val noteDao: NoteDao) {

    val allNotes = noteDao.getAllNotes()
    suspend fun insert(note: Note){
        noteDao.insert(note)
    }

    suspend fun delete(note: Note){
        noteDao.delete(note)

    }

    suspend fun update(note: Note){
        noteDao.update(note)
    }

    suspend fun deleteAllNotes(){
        noteDao.deleteAllNotes()
    }
}