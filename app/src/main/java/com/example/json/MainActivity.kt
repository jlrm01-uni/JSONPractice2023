package com.example.json

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.json.databinding.ActivityMainBinding
import com.google.gson.Gson
import java.io.File


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

        binding.deleteEverythingButton.setOnClickListener {
            showDeleteEverythingDialog()
        }

        loadNotes()
        displayNotes()
    }

    private fun showDeleteEverythingDialog() {
        val myDialog = AlertDialog.Builder(this)

        myDialog.setTitle("DELETE EVERYTHING?!")
        myDialog.setMessage("This is irreversible. Are you reaaaally sure?")

        myDialog.setPositiveButton("Get rid of it!", DialogInterface.OnClickListener { dialogInterface, i ->
            deleteEverything()
        })

        myDialog.setNegativeButton("Noooooooo", DialogInterface.OnClickListener { dialogInterface, i ->
            // Nothing to do here
        })

        myDialog.show()
    }

    private fun deleteEverything() {
        val file = File(filesDir, saveFilename)

        if (file.exists()) {
            file.delete()

            Toast.makeText(this, "Everything is gone :(", Toast.LENGTH_LONG).show()

            notesCollection.clearNotes()
            displayNotes()
        }
    }

    private fun loadNotes() {
        val file = File(filesDir, saveFilename)

        if (!file.exists()) {
            Toast.makeText(this, "No notes saved yet.", Toast.LENGTH_LONG).show()
            return
        }

        val string = file.readText()

        val loadedCollection = Gson().fromJson(string, NotesCollection::class.java)

        notesCollection = loadedCollection
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

        saveNotes()

        displayNotes()
        clearFields()
    }

    private fun saveNotes() {
        val jsonString = Gson().toJson(notesCollection)

        val file = File(filesDir, saveFilename)

        file.writeText(jsonString)

        Toast.makeText(this, "Notes saved!", Toast.LENGTH_LONG).show()
    }

    private fun clearFields() {
        binding.subjectEditText.text.clear()
        binding.contentEditText.text.clear()
    }

    private fun displayNotes() {
        binding.allNotesTextView.text = notesCollection.toString()
    }
}