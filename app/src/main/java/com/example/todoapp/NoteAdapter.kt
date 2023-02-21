package com.example.todoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter: RecyclerView.Adapter<NoteAdapter.NoteHolder>() {

    private lateinit var listener: OnItemClickListener

    private val diffCallback = object: DiffUtil.ItemCallback<Note>(){
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return newItem.title == oldItem.title &&
                    newItem.description == oldItem.description &&
                    newItem.priority == oldItem.priority
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var notes: List<Note>
        get() = differ.currentList
        set(value) {differ.submitList(value)}

    inner class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView
        var tvDescription: TextView
        var tvPriority: TextView

        init {
            tvTitle = itemView.findViewById(R.id.tv_title)
            tvDescription = itemView.findViewById(R.id.tv_description)
            tvPriority = itemView.findViewById(R.id.tv_priority)

            itemView.setOnClickListener {
                listener.onItemClick(notes[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item,parent,false)
        return NoteHolder(itemView)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun getNoteAt(position: Int): Note{
        return notes[position]
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val currNote = notes[position]
        holder.tvTitle.text = currNote.title
        holder.tvDescription.text = currNote.description
        holder.tvPriority.text = currNote.priority.toString()
    }

    interface OnItemClickListener {
        fun onItemClick(note: Note)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener = listener
    }
}