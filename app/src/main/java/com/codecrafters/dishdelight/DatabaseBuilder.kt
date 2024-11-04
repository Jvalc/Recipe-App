package com.codecrafters.dishdelight

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {

    /* Define the migration here
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Perform schema changes here
        }
    }*/

    fun buildDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "recipe-database"
        )
            //.addMigrations(MIGRATION_1_2) // Add the migration here
            .build()
    }

}