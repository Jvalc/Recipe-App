package com.codecrafters.dishdelight

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface RecipeApiService {
    // Get recommendations for recipes based on user preferences x
    @GET("/recipes/")
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

    // Add a note to a specific recipe x
    @POST("/recipes/{recipeId}/notes")
    fun addNote(
        @Path("recipeId") recipeId: String,
        @Body request: NoteRequest
    ): Call<NotesResponse>

    // Get notes for a specific recipe by user x
    @GET("/recipes/user/{userId}/{recipeId}/notes")
    fun getNotes(
        @Path("userId") userId: String,
        @Path("recipeId") recipeId: String
    ): Call<NotesResponse>

    // Share a recipe x
    @POST("/recipes/share")
    fun shareRecipe(@Body shareRequest: ShareRequest): Call<ShareResponse>

    // Download a recipe for offline use
    @POST("/users/{userId}/offlineRecipes/{recipeId}")
    fun downloadRecipe(
        @Path("userId") userId: String,
        @Path("recipeId") recipeId: String
    ): Call<DownloadResponse>
}