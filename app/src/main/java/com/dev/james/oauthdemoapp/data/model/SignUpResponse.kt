package com.dev.james.oauthdemoapp.data.model

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("message")
    val message : String
)
