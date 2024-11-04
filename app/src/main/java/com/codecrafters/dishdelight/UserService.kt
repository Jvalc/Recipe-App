package com.codecrafters.dishdelight

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST
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

    @POST("/settings/users/{userId}/token")
    fun sendFcmToken(
        @Path("userId") userId: String,
        @Body fcmTokenRequest: FcmTokenRequest
    ): Call<ResponseBody>
}

