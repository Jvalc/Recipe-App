package com.codecrafters.dishdelight

import android.util.Log
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

// Define the database version and list of entities (tables) used in the database.
// Here, we're specifying the Recipe table and setting the version to 2.
@Database(entities = [Recipe::class], version = 2)
@TypeConverters(StringListConverter::class, NoteListConverter::class)

// Define an abstract function to access the RecipeDao, which provides
// methods to interact with the Recipe table.
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    companion object {
        private const val TAG = "AppDatabase"

        // Initialize and log when the database is first created.
        init {
            Log.d(TAG, "AppDatabase initialized with version 2.")
        }
    }
}



