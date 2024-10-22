package com.example.dishdelight

import android.service.autofill.SaveRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface RecipeApiService {
    // Get recommendations for recipes based on user preferences x
    @GET("recipes/")
    fun getRecipes(): Call<List<Recipe>>

    // Search for recipes with specific parameters x
    @GET("/recipes/search")
    fun searchRecipes(@QueryMap options: Map<String, String>): Call<SearchResponse>

    // Save a recipe to a specific category x
    @POST("/recipes/save")
    fun saveRecipe(@Body recipeSaveRequest: RecipeSaveRequest): Call<SaveResponse>

    // Retrieve list of categories/files created by the user x
    @GET("/recipes/categories/{userId}")
    fun getCategories(@Path("userId") userId: String): Call<List<String>>

    // Retrieve saved recipes from a specific folder x
    @GET("/recipes/folders/{userId}/{folderName}")
    fun getSavedRecipes(
        @Path("userId") userId: String,
        @Path("folderName") folderName: String
    ): Call<List<Recipe>>

    // Add a note to a specific recipe
    @POST("{recipeId}/notes")
    fun addNote(
        @Path("recipeId") recipeId: String,
        @Body noteRequest: NoteRequest
    ): Call<NoteResponse>

    // Get notes for a specific recipe by user
    @GET("user/{userId}/{recipeId}/notes")
    fun getNotesForRecipe(
        @Path("userId") userId: String,
        @Path("recipeId") recipeId: String
    ): Call<List<Note>>

    // Share a recipe x
    @POST("recipes/share")
    fun shareRecipe(@Body shareRequest: ShareRequest): Call<ShareResponse>

    // Delete a saved recipe
    @DELETE("delete")
    fun deleteRecipe(@Body deleteRequest: DeleteRequest): Call<DeleteResponse>

    // Add a review for a specific recipe
    @POST("recipes/{recipeId}/reviews")
    fun addReview(
        @Path("recipeId") recipeId: String,
        @Body reviewRequest: ReviewRequest
    ): Call<ReviewResponse>

    // Get reviews for a specific recipe
    @GET("recipes/{recipeId}/reviews")
    fun getReviews(@Path("recipeId") recipeId: String): Call<List<Review>>

    // Download a recipe for offline use
    @POST("users/{userId}/offlineRecipes/{recipeId}")
    fun downloadRecipe(
        @Path("userId") userId: String,
        @Path("recipeId") recipeId: String
    ): Call<DownloadResponse>
}