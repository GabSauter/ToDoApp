package com.example.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.Toast

class AddNoteActivity : AppCompatActivity() {

    private val EXTRA_TITLE = "com.example.todoapp.EXTRA_TITLE"
    private val EXTRA_DESCRIPTION = "com.example.todoapp.EXTRA_DESCRIPTION"
    private val EXTRA_PRIORITY = "com.example.todoapp.EXTRA_PRIORITY"

    private lateinit var etTitle: EditText
    private lateinit var etDescription: EditText
    private lateinit var npPriority: NumberPicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        etTitle = findViewById(R.id.et_title)
        etDescription = findViewById(R.id.et_description)
        npPriority = findViewById(R.id.np_priority)

        npPriority.minValue = 1
        npPriority.maxValue = 10

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        title = "Add Note"
    }

    private fun saveNote(){
        val title = etTitle.text.toString()
        val description = etDescription.text.toString()
        val priority = npPriority.value

        if(title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show()
            return
        }

        val data = Intent()
        data.putExtra(EXTRA_TITLE, title)
        data.putExtra(EXTRA_DESCRIPTION, description)
        data.putExtra(EXTRA_PRIORITY, priority)

        setResult(RESULT_OK, data)
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater : MenuInflater = menuInflater
        menuInflater.inflate(R.menu.add_note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.save_note -> {
                saveNote()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}