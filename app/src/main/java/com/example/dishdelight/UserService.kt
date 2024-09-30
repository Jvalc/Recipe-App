package com.example.dishdelight

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserService {

    // Update user's dietary preferences
    @PUT("settings/user/{userId}/preferences")
    fun updateUserPreferences(
        @Path("userId") userId: String?,
        @Body preferencesRequest: UpdatePreferencesRequest?
    ): Call<UpdatePreferencesResponse?>?

    // Update user's notification preference
    @PUT("settings/user/notifications")
    fun updateNotificationPreference(
        @Body notificationRequest: NotificationRequest?
    ): Call<NotificationResponse?>?

    // Update user's Wi-Fi download preference
    @PUT("settings/user/wifi-preference")
    fun updateWifiPreference(
        @Body wifiPreferenceRequest: WifiPreferenceRequest?
    ): Call<WifiPreferenceResponse?>?

    // Update user's profile information
    @PUT("settings/user/profile")
    fun updateProfile(
        @Body profileUpdateRequest: ProfileUpdateRequest?
    ): Call<ProfileUpdateResponse?>?

    @GET("settings/user/profile")
    fun getUserProfile(): Call<ProfileResponse>
}

