package com.easycodingg.easynotesyt.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.easycodingg.easynotesyt.R
import com.easycodingg.easynotesyt.adapters.NoteListAdapter
import com.easycodingg.easynotesyt.databinding.FragmentNotesBinding
import com.easycodingg.easynotesyt.ui.viewmodels.NoteViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesFragment: Fragment(R.layout.fragment_notes) {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NoteViewModel by viewModels()

    private lateinit var noteListAdapter: NoteListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNotesBinding.bind(view)

        setupRecyclerView()

        viewModel.notes.observe(viewLifecycleOwner) {
            noteListAdapter.submitList(it)
        }

        binding.fabAddNote.setOnClickListener {
            findNavController().navigate(NotesFragmentDirections
                    .actionNotesFragmentToAddEditFragment())
        }
    }

    private fun setupRecyclerView() {
        noteListAdapter = NoteListAdapter()

        binding.rvNotes.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = noteListAdapter
        }

        noteListAdapter.setOnItemClickListener {
            val action = NotesFragmentDirections.actionNotesFragmentToAddEditFragment(it)
            findNavController().navigate(action)
        }

        setSwipeToDelete()
    }

    private fun setSwipeToDelete() {
        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemPosition = viewHolder.adapterPosition
                val currentNote = noteListAdapter.currentList[itemPosition]

                viewModel.deleteNote(currentNote)

                Snackbar.make(requireView(), "Note Deleted Successfully", Snackbar.LENGTH_SHORT)
                        .setAction("Undo") {
                            viewModel.insertNote(currentNote)
                        }
                        .show()
            }
        }
        ItemTouchHelper(itemTouchCallback).attachToRecyclerView(binding.rvNotes)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}