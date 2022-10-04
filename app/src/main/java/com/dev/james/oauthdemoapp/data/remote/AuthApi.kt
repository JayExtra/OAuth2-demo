package com.dev.james.oauthdemoapp.data.remote

import com.dev.james.oauthdemoapp.constants.Consts
import com.dev.james.oauthdemoapp.data.model.*
import com.dev.james.oauthdemoapp.data.remote.dto.CategoryDiagramsMainResponseDto
import com.dev.james.oauthdemoapp.data.remote.dto.CategoryMainResponseDto
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


    @GET(Consts.CATEGORY_DIAGRAMS_ENDPOINT)
    suspend fun getCategoryDiagrams() : CategoryDiagramsMainResponseDto

    @GET("${Consts.CATEGORY_DIAGRAMS_ENDPOINT}/{catId}")
    suspend fun getCategoryDetails(
        @Path(value = "catId") catId : String
    ) : CategoryMainResponseDto


}