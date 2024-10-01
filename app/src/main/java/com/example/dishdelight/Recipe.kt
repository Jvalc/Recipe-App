package com.example.dishdelight

import android.os.Parcel
import android.os.Parcelable

data class Recipe(
    val id: String = "",
    val name: String = "",
    val cookingTime: String = "",
    val servings: String = "",
    val imageUrl: String = "",
    val ingredients: List<String> = listOf(),
    val steps: List<String> = listOf(),
    val notes: List<Note> = listOf(),
    val dietaryPreference: List<String> = listOf(),
    val cuisine: String = ""
)



