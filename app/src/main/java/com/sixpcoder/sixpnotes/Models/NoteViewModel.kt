package com.sixpcoder.sixpnotes.Models

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sixpcoder.sixpnotes.Database.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class NoteViewModel(private val repository: NoteRepository):ViewModel() {

val allnotesdata:LiveData<List<Note>> = repository.allnotes
    fun insert(note: Note)=viewModelScope.launch(Dispatchers.IO)
    {
        repository.insert(note)

    }

    fun update(note: Note)=viewModelScope.launch(Dispatchers.IO)
    {
        repository.update(note)

    }

    fun delete(note: Note)=viewModelScope.launch(Dispatchers.IO)
    {
        repository.delete(note)

    }

}
class NoteViewModelFactory(private val repository: NoteRepository) :
    ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
     if (modelClass.isAssignableFrom(NoteViewModel::class.java)){
         @Suppress("UNCHECKED_CAST")
         return NoteViewModel(repository) as T
     }
        throw IllegalArgumentException("Unknown Viewmodel Class")
    }
}