package com.dev.james.oauthdemoapp.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class LoginRequest(
    @SerializedName("email")
    val email : String ,
    @SerializedName("password")
    val password : String
)
