package com.example.json

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.json.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var notesCollection: NotesCollection = NotesCollection()
    val saveFilename = "all_notes.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addNoteButton.setOnClickListener {
            addNote()
        }
    }

    private fun addNote() {
        val noteSubject = binding.subjectEditText.text.toString()
        val noteContent = binding.contentEditText.text.toString()

        if (noteSubject == "" || noteContent == "") {
            Toast.makeText(this, "Field missing!", Toast.LENGTH_LONG).show()
            return
        }

        val newNote = Notes()
        newNote.setSubject(noteSubject)
        newNote.setContent(noteContent)

        notesCollection.addNote(newNote)

//        saveNotes()

        displayNotes()
    }

    private fun displayNotes() {
        binding.allNotesTextView.text = notesCollection.toString()
    }
}