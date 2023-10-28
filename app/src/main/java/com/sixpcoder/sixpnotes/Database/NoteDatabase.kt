package com.sixpcoder.sixpnotes.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sixpcoder.sixpnotes.Models.Note
import com.sixpcoder.sixpnotes.utilities.DATABASE_NAME

@Database(entities = arrayOf(Note::class), version = 1, exportSchema = false)
public abstract class NoteDatabase:RoomDatabase() {

    abstract fun notedao():NoteDao

    companion object{

        @Volatile
         private var INSTANCE :NoteDatabase?=null

        fun getdatabase(context: Context):NoteDatabase{

            return INSTANCE?: synchronized(this){
                val instance= Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    DATABASE_NAME
                ).build()

                INSTANCE=instance

                instance


            }
        }
    }
}