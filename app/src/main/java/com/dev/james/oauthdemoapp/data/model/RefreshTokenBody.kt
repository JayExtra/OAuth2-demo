package com.dev.james.oauthdemoapp.data.model

import com.google.gson.annotations.SerializedName

data class RefreshTokenBody(
    @SerializedName("refresh_token")
    val refreshToken : String
)