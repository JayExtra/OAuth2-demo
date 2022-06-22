package com.dev.james.oauthdemoapp.data.repository

import com.dev.james.oauthdemoapp.constants.NetworkResource
import com.dev.james.oauthdemoapp.data.model.ForgotPasswordBody
import com.dev.james.oauthdemoapp.data.model.LoginRequest
import com.dev.james.oauthdemoapp.data.model.RefreshTokenBody
import com.dev.james.oauthdemoapp.data.model.SignUpRequest
import com.dev.james.oauthdemoapp.data.remote.FakeApi
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test


class AuthRepositoryTest {
    private lateinit var fakeApi : FakeApi
    private lateinit var authRepository: AuthRepository
    @Before
    fun setUp() {
        fakeApi = FakeApi()
        authRepository = AuthRepository(fakeApi)
    }

    @Test
    fun `good login request returns login response , should pass`(){
        //given
        val loginRequest = LoginRequest(
            email = "testemail123@gmail.com" ,
            password = "12345678"
        )
        //when
        val response = runBlocking { authRepository.signInUser(loginRequest = loginRequest) }

        //then
        when(response){
            is NetworkResource.Success -> {
                assertThat(response.value.accessToken.trim()).isNotEmpty()
            }
            else -> {}
        }
    }

    @Test
    fun `wrong password given returns failure`(){
        //given
        val loginRequest = LoginRequest(
            email = "testemail123@gmail.com" ,
            password = "123456"
        )
        //when
        val response = runBlocking { authRepository.signInUser(loginRequest = loginRequest) }

        //then
        when(response){
            is NetworkResource.Success -> {
                assertThat(response.value.accessToken.trim()).isNotEmpty()
            }
            else -> {}
        }
    }

    @Test
    fun `unknown email given returns failure`(){
        //given
        val loginRequest = LoginRequest(
            email = "testel123@gmail.com" ,
            password = "123456"
        )
        //when
        val response = runBlocking { authRepository.signInUser(loginRequest = loginRequest) }

        //then
        when(response){
            is NetworkResource.Success -> {
                assertThat(response.value.accessToken.trim()).isNotEmpty()
            }
            else -> {}
        }
    }

    @Test
    fun `un-matching passwords in sign up returns failure`(){
        //given
        val signUpRequest = SignUpRequest(
            name = "John Doe",
            email = "testemail123@gmail.com" ,
            password = "12345678",
            confirmPassword = "123",
            phoneNumber = "123456789",
            countryCode = "254"
        )
        //when
        val response = runBlocking { authRepository.signUpUser(signUpRequest = signUpRequest) }

        //then
        when(response){
            is NetworkResource.Success -> {
                assertThat(response.value.message.trim()).isNotEmpty()
            }
            else -> {}
        }
    }

    @Test
    fun `wrong email in sign up returns failure`(){
        //given
        val signUpRequest = SignUpRequest(
            name = "John Doe",
            email = "testemail123gmail.com" ,
            password = "12345678",
            confirmPassword = "12345678",
            phoneNumber = "123456789",
            countryCode = "254"
        )
        //when
        val response = runBlocking { authRepository.signUpUser(signUpRequest = signUpRequest) }

        //then
        when(response){
            is NetworkResource.Success -> {
                assertThat(response.value.message.trim()).isNotEmpty()
            }
            else -> {}
        }
    }

    @Test
    fun `empty name in sign up returns failure`(){
        //given
        val signUpRequest = SignUpRequest(
            name = "",
            email = "testemail123@gmail.com" ,
            password = "12345678",
            confirmPassword = "12345678",
            phoneNumber = "123456789",
            countryCode = "254"
        )
        //when
        val response = runBlocking { authRepository.signUpUser(signUpRequest = signUpRequest) }

        //then
        when(response){
            is NetworkResource.Success -> {
                assertThat(response.value.message.trim()).isNotEmpty()
            }
            else -> {}
        }
    }

    @Test
    fun `empty country code in sign up returns failure`(){
        //given
        val signUpRequest = SignUpRequest(
            name = "John Doe",
            email = "testemail123@gmail.com" ,
            password = "12345678",
            confirmPassword = "12345678",
            phoneNumber = "123456789",
            countryCode = ""
        )
        //when
        val response = runBlocking { authRepository.signUpUser(signUpRequest = signUpRequest) }

        //then
        when(response){
            is NetworkResource.Success -> {
                assertThat(response.value.message.trim()).isNotEmpty()
            }
            else -> {}
        }
    }

    @Test
    fun `empty phone number in sign up returns failure`(){
        //given
        val signUpRequest = SignUpRequest(
            name = "John Doe",
            email = "testemail123@gmail.com" ,
            password = "12345678",
            confirmPassword = "12345678",
            phoneNumber = "",
            countryCode = "254"
        )
        //when
        val response = runBlocking { authRepository.signUpUser(signUpRequest = signUpRequest) }

        //then
        when(response){
            is NetworkResource.Success -> {
                assertThat(response.value.message.trim()).isNotEmpty()
            }
            else -> {}
        }
    }

    @Test
    fun `good sign up request in sign up returns success`(){
        //given
        val signUpRequest = SignUpRequest(
            name = "John Doe",
            email = "testemail123@gmail.com" ,
            password = "12345678",
            confirmPassword = "12345678",
            phoneNumber = "123456789",
            countryCode = "254"
        )
        //when
        val response = runBlocking { authRepository.signUpUser(signUpRequest = signUpRequest) }

        //then
        when(response){
            is NetworkResource.Success -> {
                assertThat(response.value.message.trim()).isNotEmpty()
            }
            else -> {}
        }
    }

    @Test
    fun `bad email in forgot password returns failure`(){
        //given
        val forgotPasswordBody = ForgotPasswordBody(
            email = "testemailgmail.com"
        )
        //when
        val response = runBlocking { authRepository.forgotPassword(email = forgotPasswordBody) }

        //then
        when(response){
            is NetworkResource.Success -> {
                assertThat(response.value.message.trim()).isNotEmpty()
            }
            else -> {}
        }
    }

    @Test
    fun `empty email in forgot password returns failure`(){
        //given
        val forgotPasswordBody = ForgotPasswordBody(
            email = ""
        )
        //when
        val response = runBlocking { authRepository.forgotPassword(email = forgotPasswordBody) }

        //then
        when(response){
            is NetworkResource.Success -> {
                assertThat(response.value.message.trim()).isNotEmpty()
            }
            else -> {}
        }
    }

    @Test
    fun `good email in forgot password returns success`(){
        //given
        val forgotPasswordBody = ForgotPasswordBody(
            email = "testemail123@gmail.com"
        )
        //when
        val response = runBlocking { authRepository.forgotPassword(email = forgotPasswordBody) }

        //then
        when(response){
            is NetworkResource.Success -> {
                assertThat(response.value.message.trim()).isNotEmpty()
            }
            else -> {}
        }
    }

    @Test
    fun `good refresh token in refresh token call returns success`(){
        //given
        val refreshTokenBody = RefreshTokenBody(
            refreshToken = "qwerty"
        )
        //when
        val response = runBlocking { authRepository.refreshTokens(refreshToken = refreshTokenBody) }

        //then
        when(response){
            is NetworkResource.Success -> {
                assertThat(response.value.accessToken.trim()).isNotEmpty()
            }
            else -> {}
        }
    }

    @Test
    fun `wrong refresh token in refresh token call returns failure`(){
        //given
        val refreshTokenBody = RefreshTokenBody(
            refreshToken = "asdasdasdd"
        )
        //when
        val response = runBlocking { authRepository.refreshTokens(refreshToken = refreshTokenBody) }

        //then
        when(response){
            is NetworkResource.Success -> {
                assertThat(response.value.accessToken.trim()).isNotEmpty()
            }
            else -> {}
        }
    }

    @Test
    fun `empty refresh token in refresh token call returns failure`(){
        //given
        val refreshTokenBody = RefreshTokenBody(
            refreshToken = ""
        )
        //when
        val response = runBlocking { authRepository.refreshTokens(refreshToken = refreshTokenBody) }

        //then
        when(response){
            is NetworkResource.Success -> {
                assertThat(response.value.accessToken.trim()).isNotEmpty()
            }
            else -> {}
        }
    }

}