package com.dev.james.oauthdemoapp.data.remote

import com.dev.james.oauthdemoapp.data.model.*
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
    suspend fun refreshTokens(
        @Body refreshToken : RefreshTokenBody
    ) : LoginResponse


    @POST("auth/password/forgot")
    suspend fun forgotPassword(
        @Body email : ForgotPasswordBody
    ) : ForgotPasswordResponse


}