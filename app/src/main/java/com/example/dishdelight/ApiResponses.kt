package com.example.dishdelight

data class SearchResponse(val results: List<Recipe>) //x
data class RecipeSaveRequest(val userId: String, val category: String, val recipeId: String) //x
data class SaveResponse(val message: String, val id: String) //x
data class NoteRequest(val note: String, val userId: String)
data class NoteResponse(val message: String, val noteId: String, val note: String)
data class ShareRequest( val recipeId: String) //x
data class ShareResponse(val message: String, val shareableText: String) //x
data class DeleteRequest(val userId: String, val recipeId: String, val category: String)
data class DeleteResponse(val message: String)
data class ReviewRequest(val userId: String, val rating: Int, val comment: String)
data class ReviewResponse(val message: String)
data class DownloadResponse(val message: String)
data class Note1(val id: String, val note: String, val timestamp: String)
data class Review(val userId: String, val rating: Int, val comment: String, val recipeId: String)


data class UpdatePreferencesRequest(val dietaryPreferences: List<String>)
data class UpdatePreferencesResponse(val message: String)
data class ProfileUpdateRequest(val userId: String, val username: String)
data class ProfileUpdateResponse(val message: String)
data class ProfileResponse( val name: String)

data class User(val userId: String, val email: String, val name: String, val username: String)

