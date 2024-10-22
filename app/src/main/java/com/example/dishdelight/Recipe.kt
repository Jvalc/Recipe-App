package com.example.dishdelight

import android.os.Parcel
import android.os.Parcelable
data class Recipe(
    val id: String = "",
    val recipeName: String = "",
    val cookingTime: String = "",
    val cuisineType: String= "",
    val dietaryPreferences: List<String> = listOf(),
    val servingSize: String = "",

    val imageUrl: String = "",
    val ingredients: List<String> = listOf(),
    val steps: List<String> = listOf(),
    val notes: List<Note> = listOf()
)



