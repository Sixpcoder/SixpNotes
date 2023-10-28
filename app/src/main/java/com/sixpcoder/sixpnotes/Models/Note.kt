package com.sixpcoder.sixpnotes.Models

import androidx.room.*


@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)val id :Int,
    @ColumnInfo(name = "title")val title :String,
    @ColumnInfo(name = "note")val note:String,
    @ColumnInfo(name = "date")val date :String
)
