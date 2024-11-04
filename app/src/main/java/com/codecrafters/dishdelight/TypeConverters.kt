package com.codecrafters.dishdelight

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StringListConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromStringList(list: List<String>): String = gson.toJson(list)

    @TypeConverter
    fun toStringList(json: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }
}

class NoteListConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromNoteList(list: List<Note>): String = gson.toJson(list)

    @TypeConverter
    fun toNoteList(json: String): List<Note> {
        val type = object : TypeToken<List<Note>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }
}