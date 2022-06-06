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
    suspend fun signInUser(
       @Body loginRequest: LoginRequest
    ) : LoginResponse

    @POST("auth/token/refresh")
    @FormUrlEncoded
    suspend fun refreshTokens(
        @Field("refresh_token") refreshToken : String
    ) : LoginResponse
}