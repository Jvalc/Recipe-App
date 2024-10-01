package com.example.dishdelight

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserService {
    // Update user's profile information
    @PUT("/user/profile")
    fun updateUserProfile
        (@Body request: ProfileUpdateRequest
    ): Call<ProfileUpdateResponse>

    // Update user's dietary preferences
    @PATCH("/users/{userId}/preferences")
    fun updateUserPreferences(
        @Path("userId") userId: String,
        @Body request: UpdatePreferencesRequest
    ): Call<UpdatePreferencesResponse>
}

