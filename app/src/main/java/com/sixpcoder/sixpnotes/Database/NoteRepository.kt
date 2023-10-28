package com.sixpcoder.sixpnotes.Database

import androidx.lifecycle.LiveData
import com.sixpcoder.sixpnotes.Models.Note

class NoteRepository(private val noteDao: NoteDao) {

    val allnotes:LiveData<List<Note>> = noteDao.getallnote()

    suspend fun insert(note: Note){
        noteDao.insert(note)

    }

    suspend fun delete(note: Note){
        noteDao.delete(note)

    }

    suspend fun update(note: Note){
        noteDao.update(note.id,note.title,note.note)
    }
}