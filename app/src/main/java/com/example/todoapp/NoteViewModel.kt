package com.example.todoapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class NoteViewModel(
    application: Application,
) : AndroidViewModel(application) {

    private var repository: NoteRepository
    var allNotes: LiveData<List<Note>>

    init {
        repository = NoteRepository(application)
        allNotes = repository.allNotes
    }

    fun insert(note: Note){
        repository.insert(note)
    }

    fun delete(note: Note){
        repository.delete(note)
    }

    fun update(note: Note){
        repository.update(note)
    }

    fun deleteAllNotes(){
        repository.deleteAllNotes()
    }

}