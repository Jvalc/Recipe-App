package com.example.dishdelight

data class Folder(
    val recipeID: String = "",
    val title: String = "",
    val imageUrl: String = "",
    val cookingTime: String = "",
    val servings: String = "",
    val ingredients: List<String> = emptyList(),
    val steps: List<String> = emptyList(),
    val notes: List<Note> = emptyList(),
    val rating: List<Ratings> = emptyList()
)
