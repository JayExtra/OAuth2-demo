package com.dev.james.oauthdemoapp.data.remote


import com.dev.james.oauthdemoapp.data.model.ForgotPasswordBody
import com.dev.james.oauthdemoapp.data.model.LoginRequest
import com.dev.james.oauthdemoapp.data.model.RefreshTokenBody
import com.dev.james.oauthdemoapp.data.model.SignUpRequest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class AuthApiServiceTest {

    private lateinit var authService : AuthApi
    private lateinit var server : MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        authService = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }

    private fun enqueueMockResponse(fileName : String){
        javaClass.classLoader?.let {
            val inputStream = it.getResourceAsStream(fileName)
            val source = inputStream.source().buffer()
            val mockResponse = MockResponse()
            mockResponse.setBody(source.readString(Charsets.UTF_8))
            server.enqueue(mockResponse)
        }
    }

    @Test
    fun `sendSignUpRequestBody returns signUpResponseBody`(){
        runBlocking {
            //given
            //prepare fake response
            enqueueMockResponse("SignUpResponse.json")
            val signUpRequest = SignUpRequest(
                name = "John Doe",
                email = "johndoe@gmail.com",
                password = "12345678a",
                confirmPassword = "12345678a",
                phoneNumber = "0711223344",
                countryCode = "254"
            )
            //when
            //send request to the mock web server
            val responseBody = authService.signUp(signUpRequest = signUpRequest)

            //then
            assertThat(responseBody).isNotNull()
        }
    }

    @Test
    fun `sendLoginRequest returns loginResponse`(){

        runBlocking {
            //given
            enqueueMockResponse("LoginResponse.json")
            val loginRequest = LoginRequest(
                email = "testemail123@gmail.com" ,
                password = "12345678a"
            )
            //when
            val responseBody = authService.signInUser(loginRequest = loginRequest)

            //then
            assertThat(responseBody).isNotNull()
        }

    }

    @Test
    fun ` sendRefreshTokenRequest returns newRefreshTokensResponse`(){
        runBlocking {
            //given
            enqueueMockResponse("LoginResponse.json")
            val refreshTokenRequest = RefreshTokenBody(
                refreshToken = "ksjna.asdasdasdas.asddasd.lasdnasldsdadasdasdasd"
            )
            //when
            val responseBody = authService.refreshTokens(refreshToken = refreshTokenRequest)
            //then
            assertThat(responseBody).isNotNull()
        }
    }


    @Test
    fun ` sendPasswordResetRequest returns forgotPasswordResponse`(){
        runBlocking {
            //given
            enqueueMockResponse("ForgotPasswordResponse.json")
            val forgotPasswordRequest = ForgotPasswordBody(
                email = "testemail123@gmail.com"
            )
            //when
            val responseBody = authService.forgotPassword(forgotPasswordRequest)
            //then
            assertThat(responseBody).isNotNull()
        }
    }


    @After
    fun tearDown(){
        server.shutdown()
    }
}