package com.example.dishdelight

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
data class Recipe(
    val id: String = "",
    val recipeName: String = "",
    val cookingTime: String = "",
    val cuisineType: String= "",
    val dietaryPreferences: List<String> = listOf(),
    val servingSize: String = "",

    val imageUrl: String = "",
    val ingredients: List<String> = listOf(),
    val steps: List<String> = listOf()
)



