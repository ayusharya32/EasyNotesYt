package com.easycodingg.easynotesyt.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.easycodingg.easynotesyt.R
import com.easycodingg.easynotesyt.data.entities.Note
import com.easycodingg.easynotesyt.databinding.FragmentAddEditNoteBinding
import com.easycodingg.easynotesyt.ui.viewmodels.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEditNoteFragment: Fragment(R.layout.fragment_add_edit_note) {

    private var _binding: FragmentAddEditNoteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NoteViewModel by viewModels()
    private val args: AddEditNoteFragmentArgs by navArgs()

    private var currentNote: Note? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddEditNoteBinding.bind(view)

        currentNote = args.note

        currentNote?.let { note ->
            binding.apply {
                etTitle.setText(note.title)
                etDescription.setText(note.description)
                etDescription.requestFocus()

                val lastModifiedString = "Last Modified: ${note.createdDateFormatted}"
                tvLastModified.text = lastModifiedString
                tvLastModified.visibility = View.VISIBLE
            }
        }
    }

    private fun saveNote() {
        binding.apply {
            val title = etTitle.text.trim().toString()
            val description = etDescription.text.trim().toString()

            if(title.isEmpty() || description.isEmpty()) {
                return
            }

            currentNote?.let {
                if(title == it.title && description == it.description) {
                    return
                }
            }
            val newNote = if(currentNote == null) {
                Note(title, description)
            } else {
                currentNote?.apply {
                    this.title = title
                    this.description = description
                    this.created = System.currentTimeMillis()
                }
            }
            if (newNote != null) {
                viewModel.insertNote(newNote)
                Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        saveNote()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}