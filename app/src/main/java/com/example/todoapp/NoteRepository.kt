package com.example.todoapp

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class NoteRepository(application: Application) {

    private var database: NoteDatabase
    private lateinit var noteDao: NoteDao
    val allNotes = noteDao.getAllNotes()

    init {
        database = NoteDatabase.getInstance(application)
        noteDao = database.noteDao()
    }

    fun insert(note: Note){
        CoroutineScope(IO).launch {
            noteDao.insert(note)
        }
    }

    fun delete(note: Note){
        CoroutineScope(IO).launch {
            noteDao.delete(note)
        }
    }

    fun update(note: Note){
        CoroutineScope(IO).launch {
            noteDao.update(note)
        }
    }

    fun deleteAllNotes(){
        CoroutineScope(IO).launch {
            noteDao.deleteAllNotes()
        }
    }
}