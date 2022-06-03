package com.dev.james.oauthdemoapp.data.remote

import com.dev.james.oauthdemoapp.data.model.LoginRequest
import com.dev.james.oauthdemoapp.data.model.LoginResponse
import com.dev.james.oauthdemoapp.data.model.SignUpRequest
import com.dev.james.oauthdemoapp.data.model.SignUpResponse
import retrofit2.http.*

interface AuthApi {
    @POST("auth/register")
   // @FormUrlEncoded
    suspend fun signUp( @Body signUpRequest: SignUpRequest) : SignUpResponse

    @POST("auth/login")
    @FormUrlEncoded
    suspend fun signInUser(
        @Field("email") email : String ,
        @Field("password") password : String
    ) : LoginResponse
}