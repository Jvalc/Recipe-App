package com.example.dishdelight

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserService {
    // Update user's profile information
    @PUT("/settings/user/profile")
    fun updateUserProfile(
        @Body request: UserProfileUpdateRequest
    ): Call<UpdateResponse>

    // Update user's dietary preferences
    @PATCH("/settings/users/{userId}/preferences")
    fun updateDietaryPreferences(
        @Path("userId") userId: String,
        @Body dietaryPreferences: UpdateDietaryPreferencesRequest
    ): Call<UpdateResponse>
}

