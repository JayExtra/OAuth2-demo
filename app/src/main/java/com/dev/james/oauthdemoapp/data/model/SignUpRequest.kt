package com.dev.james.oauthdemoapp.data.model

import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("name")
    val name : String ,
    @SerializedName("email")
    val email : String ,
    @SerializedName("password")
    val password : String ,
    @SerializedName("confirm_password")
    val confirmPassword : String ,
    @SerializedName("country_code")
    val countryCode : String ,
    @SerializedName("phone_number")
    val phoneNumber : String ,
)
