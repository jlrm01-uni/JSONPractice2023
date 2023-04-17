package com.example.json

class NotesCollection {
    var all_notes: MutableList<Notes> = mutableListOf()

    fun addNote(note: Notes) {
        all_notes.add(note)
    }

    fun clearNotes() {
        all_notes.clear()
    }

    override fun toString(): String {
        if (all_notes.size == 0) {
            return "Nothing to see here but the chickens"
        } else {
            return all_notes.joinToString()
        }
    }
}