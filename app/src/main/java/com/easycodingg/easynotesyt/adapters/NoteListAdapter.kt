package com.easycodingg.easynotesyt.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.easycodingg.easynotesyt.data.entities.Note
import com.easycodingg.easynotesyt.databinding.ListItemNoteBinding

class NoteListAdapter: ListAdapter<Note, NoteListAdapter.NoteViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ListItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = getItem(position)

        if(currentNote != null && position != RecyclerView.NO_POSITION) {
            holder.bind(currentNote)
        }
    }

    inner class NoteViewHolder(private val binding: ListItemNoteBinding): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(getItem(adapterPosition))
                }
            }
        }

        fun bind(currentNote: Note) {
            binding.apply {
                tvNoteTitle.text = currentNote.title
                tvNoteDescription.text = currentNote.description

                val lastModifiedString = "Last Modified: ${currentNote.createdDateFormatted}"
                tvNoteCreatedDate.text = lastModifiedString
            }
        }
    }

    private var onItemClickListener: ((Note) -> Unit)? = null

    fun setOnItemClickListener(listener: (Note) -> Unit) {
        onItemClickListener = listener
    }

    class DiffCallback: DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note) =
                oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Note, newItem: Note) =
                oldItem == newItem
    }
}