package com.example.todoapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
class Note(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val priority: Int
)