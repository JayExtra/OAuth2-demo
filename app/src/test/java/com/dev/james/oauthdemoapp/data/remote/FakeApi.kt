package com.dev.james.oauthdemoapp.data.remote

import com.dev.james.oauthdemoapp.data.model.*

class FakeApi : AuthApi {

    override suspend fun signUp(signUpRequest: SignUpRequest): SignUpResponse {
        return if(isEmailOkay(signUpRequest.email) &&
            isPasswordEmpty(signUpRequest.password) &&
            isCountryCodeEmpty(signUpRequest.countryCode) &&
            isPhoneNumberEmpty(signUpRequest.phoneNumber) &&
            doPasswordsMatch(signUpRequest.password , signUpRequest.confirmPassword) &&
            signUpRequest.name.trim().isNotBlank()
        ){
            SignUpResponse(
                message = "Successful sign up"
            )
        }else {
            SignUpResponse(
                message = ""
            )
        }
    }

    override suspend fun signInUser(loginRequest: LoginRequest): LoginResponse {
        val fakeSavedEmail = "testemail123@gmail.com"
        val fakePasword = "12345678"
        return if(
            loginRequest.email == fakeSavedEmail
            && loginRequest.email.trim().isNotBlank() &&
                    loginRequest.password.trim().isNotBlank() &&
                    loginRequest.password == fakePasword
        ){
            LoginResponse(
                accessToken = "oadlasdasldjasldjas" ,
                refreshToken = "ajkdhadnladasdasodasd" ,
                accessTokenExpiry = 187235123,
                refreshTokenExpiry = 1862462343
            )
        }else{
            LoginResponse(
                accessTokenExpiry = 0 ,
                refreshTokenExpiry = 0 ,
                accessToken = "" ,
                refreshToken = ""
            )
        }
    }

    override suspend fun refreshTokens(refreshToken: RefreshTokenBody): LoginResponse {
        val fakeRefreshTokenBody = RefreshTokenBody(refreshToken = "qwerty")
        return if(refreshToken.refreshToken.trim().isNotBlank() && refreshToken.refreshToken == fakeRefreshTokenBody.refreshToken){
            LoginResponse(
                accessToken = "oadlasdasldjasldjas" ,
                refreshToken = "ajkdhadnladasdasodasd" ,
                accessTokenExpiry = 187235123,
                refreshTokenExpiry = 1862462343
            )
        }else {
            LoginResponse(
                accessTokenExpiry = 0 ,
                refreshTokenExpiry = 0 ,
                accessToken = "" ,
                refreshToken = ""
            )
        }
    }

    override suspend fun forgotPassword(emailBody: ForgotPasswordBody): ForgotPasswordResponse {
        val fakeSavedEmail = "testemail123@gmail.com"
        return if(
            fakeSavedEmail == emailBody.email && isEmailOkay(email = emailBody.email)){
            ForgotPasswordResponse(
                message = "email sent"
            )
        }else {
            ForgotPasswordResponse(
                message = ""
            )
        }
    }

    private fun isEmailOkay(email : String) : Boolean {
        return email.trim().isNotBlank() && email.contains("@") && email.contains(".com")
    }
    private fun doPasswordsMatch(pass : String , confirmPass : String) : Boolean {
        return pass == confirmPass
    }



    private fun isPhoneNumberEmpty(number : String) : Boolean = number.trim().isNotBlank()
    private fun isCountryCodeEmpty(code : String) : Boolean = code.trim().isNotBlank()
    private fun isPasswordEmpty(pass : String) : Boolean = pass.trim().isNotBlank()
}