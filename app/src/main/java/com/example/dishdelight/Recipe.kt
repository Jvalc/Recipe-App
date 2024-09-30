package com.example.dishdelight

import android.os.Parcel
import android.os.Parcelable

data class Recipe(
    val name: String = "",
    val cookingTime: String = "",
    val servings: String = "",
    val imageUrl: String = "",
    val ingredients: List<String> = listOf(),
    val steps: List<String> = listOf(),
    val notes: List<String> = listOf(),
    val dietaryPreference: List<String> = listOf(),
    val cuisine: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.createStringArrayList() ?: arrayListOf(),
        parcel.createStringArrayList() ?: arrayListOf(),
        parcel.createStringArrayList() ?: arrayListOf(),
        parcel.createStringArrayList() ?: arrayListOf(),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(cookingTime)
        parcel.writeString(servings)
        parcel.writeString(imageUrl)
        parcel.writeStringList(ingredients)
        parcel.writeStringList(steps)
        parcel.writeStringList(notes)
        parcel.writeStringList(dietaryPreference)
        parcel.writeString(cuisine)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Recipe> {
        override fun createFromParcel(parcel: Parcel): Recipe {
            return Recipe(parcel)
        }

        override fun newArray(size: Int): Array<Recipe?> {
            return arrayOfNulls(size)
        }
    }
}

