package com.dev.james.oauthdemoapp.data.model

import com.google.gson.annotations.SerializedName

data class ForgotPasswordBody(
    @SerializedName("email")
    val email : String
)
