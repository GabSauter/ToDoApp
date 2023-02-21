package com.example.todoapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    val ADD_NOTE_REQUEST = 1
    val EDIT_NOTE_REQUEST = 2

    lateinit var noteViewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAddNote = findViewById<FloatingActionButton>(R.id.btn_add_note)
        btnAddNote.setOnClickListener {
            val intent = Intent(this@MainActivity, AddNoteActivity::class.java)
            resultLauncherCreate.launch(intent)
        }

        val rv: RecyclerView = findViewById(R.id.rv)
        rv.layoutManager = LinearLayoutManager(this)
        rv.setHasFixedSize(true)

        val adapter = NoteAdapter()
        rv.adapter = adapter

        noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]
        noteViewModel.allNotes.observe(this, Observer{
            adapter.notes = it
        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                noteViewModel.delete(adapter.getNoteAt(viewHolder.adapterPosition))
                Toast.makeText(this@MainActivity, "Note deleted", Toast.LENGTH_SHORT).show()
            }
        }).attachToRecyclerView(rv)


        adapter.setOnItemClickListener(object : NoteAdapter.OnItemClickListener {
            override fun onItemClick(note: Note) {
                val intent = Intent(this@MainActivity, AddNoteActivity::class.java)
                intent.putExtra("EXTRA_TITLE", note.title)
                intent.putExtra("EXTRA_DESCRIPTION", note.description)
                intent.putExtra("EXTRA_PRIORITY", note.priority)
                intent.putExtra("EXTRA_ID", note.id)
                resultLauncherEdit.launch(intent)
            }
        })
    }

    private var resultLauncherCreate = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if(result.resultCode == Activity.RESULT_OK){
            val title = result.data?.getStringExtra("EXTRA_TITLE")
            val description = result.data?.getStringExtra("EXTRA_DESCRIPTION")
            val priority = result.data?.getIntExtra("EXTRA_PRIORITY", 1)

            val note = Note(0, title!!, description!!, priority!!)
            noteViewModel.insert(note)

            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show()
        } else{
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show()
        }
    }

    private var resultLauncherEdit = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if(result.resultCode == Activity.RESULT_OK){

            val id = result.data?.getIntExtra("EXTRA_ID", -1)
            val title = result.data?.getStringExtra("EXTRA_TITLE")
            val description = result.data?.getStringExtra("EXTRA_DESCRIPTION")
            val priority = result.data?.getIntExtra("EXTRA_PRIORITY", 1)

            if(id == -1){
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show()
            }else{
                val note = Note(id!!, title!!, description!!, priority!!)
                noteViewModel.update(note)
                Toast.makeText(this, "Note edited and saved", Toast.LENGTH_SHORT).show()
            }
        } else{
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.delete_all_notes ->{
                noteViewModel.deleteAllNotes()
                Toast.makeText(this, "All notes deleted", Toast.LENGTH_LONG).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}