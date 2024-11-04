package com.codecrafters.dishdelight

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

data class SearchResponse(val results: List<Recipe>) //x
data class RecipeSaveRequest(val userId: String, val category: String, val recipeId: String) //x
data class SaveResponse(val message: String, val id: String) //x
data class NoteRequest(val note: String, val userId: String) //x
data class NotesResponse(val notes: List<Note>) //x
data class ShareRequest( val recipeId: String) //x
data class ShareResponse(val message: String, val shareableText: String) //x
data class DownloadResponse(val message: String)
data class Note(val id: String, val note: String, val timestamp: Timestamp, val userId: String) //x
data class Timestamp( val _seconds: Long, val _nanoseconds: Int) //x
data class UserProfileUpdateRequest( val userId: String, val username: String) //x
data class UpdateResponse( val message: String) //x
data class UpdateDietaryPreferencesRequest( val dietaryPreferences: List<String>) //x
data class FcmTokenRequest(val fcmToken: String) //x

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




