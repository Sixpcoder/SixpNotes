package com.sixpcoder.sixpnotes.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sixpcoder.sixpnotes.Models.Note
import java.net.IDN

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM notes ORDER BY id ASC" )
    fun getallnote():LiveData<List<Note>>

    @Query("UPDATE notes Set title = :title, note=:note WHERE id=:id")
    suspend fun update(id:Int,title:String,note:String)
}