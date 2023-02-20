package com.example.todoapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.RoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(
    application: Application,
) : AndroidViewModel(application) {

    private var repository: NoteRepository
    var allNotes: LiveData<List<Note>>

    init {
        val noteDB = NoteDatabase.getInstance(application).noteDao()
        repository = NoteRepository(noteDB)
        allNotes = repository.allNotes
    }

    fun insert(note: Note){
        viewModelScope.launch (Dispatchers.IO) {
            repository.insert(note)
        }
    }

    fun delete(note: Note){
        viewModelScope.launch (Dispatchers.IO){
            repository.delete(note)
        }
    }

    fun update(note: Note){
        viewModelScope.launch (Dispatchers.IO){
            repository.update(note)
        }
    }

    fun deleteAllNotes(){
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteAllNotes()
        }
    }

}