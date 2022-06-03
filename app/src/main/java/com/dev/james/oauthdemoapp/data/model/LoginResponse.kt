package com.dev.james.oauthdemoapp.data.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("access_token")
    val accessToken : String ,
    @SerializedName("refresh_token")
    val refreshToken : String ,
    @SerializedName("access_token_expires_in")
    val accessTokenExpiry : Int ,
    @SerializedName("refresh_token_expires_in")
    val refreshTokenExpiry : Int
)
