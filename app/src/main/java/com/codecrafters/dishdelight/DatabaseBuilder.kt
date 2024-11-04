package com.codecrafters.dishdelight

import android.content.Context
import androidx.room.Room

// Singleton object to create and provide an instance of the database
object DatabaseBuilder {
    // Function to build and return an instance of AppDatabase
    fun buildDatabase(context: Context): AppDatabase {
        // Use Room's database builder to create the database instance
        return Room.databaseBuilder(
            context.applicationContext,            // Application context to avoid memory leaks
            AppDatabase::class.java,               // The database class to instantiate
            "recipe-database"                      // Name of the database file
        )
            .build()                              // Build and return the database instance
    }
}