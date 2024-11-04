package com.codecrafters.dishdelight

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

// SearchResponse: Represents the response from a recipe search request.
data class SearchResponse(val results: List<Recipe>)

// RecipeSaveRequest: Holds the necessary data to save a recipe for a specific user.
data class RecipeSaveRequest(val userId: String, val category: String, val recipeId: String)

// SaveResponse: Returns a message and ID after a save operation.
data class SaveResponse(val message: String, val id: String)

// NoteRequest: Contains a note and the user ID of the user who created it.
data class NoteRequest(val note: String, val userId: String)

// NotesResponse: Contains a list of notes returned from a request.
data class NotesResponse(val notes: List<Note>)

// ShareRequest: Holds the recipe ID for a recipe sharing request.
data class ShareRequest( val recipeId: String)

// ShareResponse: Contains the message and shareable text after a sharing operation.
data class ShareResponse(val message: String, val shareableText: String)

// DownloadResponse: Holds a message indicating the result of a download request.
data class DownloadResponse(val message: String)

// Note: Represents a user's note, including its ID, content, timestamp, and the user's ID.
data class Note(val id: String, val note: String, val timestamp: Timestamp, val userId: String)

// Timestamp: Represents a timestamp with seconds and nanoseconds.
data class Timestamp( val _seconds: Long, val _nanoseconds: Int)

// UserProfileUpdateRequest: Contains the user ID and new username for a profile update.
data class UserProfileUpdateRequest( val userId: String, val username: String)

// UpdateResponse: Returns a message after a profile update operation.
data class UpdateResponse( val message: String)

// UpdateDietaryPreferencesRequest: Holds a list of dietary preferences for an update request.
data class UpdateDietaryPreferencesRequest( val dietaryPreferences: List<String>)

// FcmTokenRequest: Holds the FCM token for push notifications.
data class FcmTokenRequest(val fcmToken: String)

// Recipe: Represents a recipe entity in the database with primary key, name, and details.
@Entity(tableName = "Recipes")
data class Recipe(
    @PrimaryKey var id: String = "",
    var recipeName: String = "",
    var cookingTime: String = "",
    var cuisineType: String = "",
    var servingSize: String = "",
    var imageUrl: String = "",

    @TypeConverters(StringListConverter::class)
    var dietaryPreferences: List<String> = listOf(),

    @TypeConverters(StringListConverter::class)
    var ingredients: List<String> = listOf(),

    @TypeConverters(StringListConverter::class)
    var steps: List<String> = listOf(),
)




