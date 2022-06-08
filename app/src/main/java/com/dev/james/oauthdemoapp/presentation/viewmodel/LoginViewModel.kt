package com.dev.james.oauthdemoapp.presentation.viewmodel

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.james.oauthdemoapp.constants.NetworkResource
import com.dev.james.oauthdemoapp.data.local.datastore.DatastoreManager
import com.dev.james.oauthdemoapp.data.model.LoginRequest
import com.dev.james.oauthdemoapp.data.model.LoginResponse
import com.dev.james.oauthdemoapp.domain.SignInUsecase
import com.dev.james.oauthdemoapp.domain.ValidationResults
import com.dev.james.oauthdemoapp.domain.session.SessionManager
import com.dev.james.oauthdemoapp.presentation.screens.events.LoginScreenEvents
import com.dev.james.oauthdemoapp.presentation.screens.states.LoginScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInUsecase: SignInUsecase ,
    private val dataStoreManager : DatastoreManager ,
    private val sessionManager: SessionManager
) : ViewModel() {

    var state by mutableStateOf(LoginScreenState())

    private var loginValidationAuthEvents = Channel<LoginScreenValidationAndAuthEvent>()
    val loginValidationAuthEventsChannel = loginValidationAuthEvents.receiveAsFlow()

    /**

  init {
      //  val accessToken = dataStoreManager.readAccessToken(PrefKeysManager.ACCESS_TOKEN)
        val refreshToken = dataStoreManager.readRefreshToken(PrefKeysManager.REFRESH_TOKEN)

        CoroutineScope(Dispatchers.IO).launch {
            refreshToken.collect {
                Log.d("LoginViewModel", "access and refresh tokens: $it")

                sessionManager.refreshTokens()

            }
        }
    }

    **/



    fun onEvent( event : LoginScreenEvents) {
        when(event){
            is LoginScreenEvents.OnEmailFieldTextChange ->{
                state = state.copy(email = event.email)
            }
            is LoginScreenEvents.OnPasswordFieldTextChange ->{
                state = state.copy(password = event.password)
            }
            is LoginScreenEvents.Submit ->{
                state = state.copy(showProgressBar = true)
                submitForValidation()
            }
        }

    }

    private fun submitForValidation() {
        val emailValidationResults = validateEmail(state.email)

        val hasError = listOf(
            emailValidationResults
        ).any{ !it.success }

        state = state.copy(
            emailError = emailValidationResults.errorMessage ,
            showProgressBar = false
        )

        if(hasError){ return }

        viewModelScope.launch {
            loginValidationAuthEvents.send(LoginViewModel.LoginScreenValidationAndAuthEvent.ValidationSuccess)
        }
    }

    fun signInUser() = viewModelScope.launch {
        val loginRequest = LoginRequest(
            email = state.email,
            password = state.password
        )
        val result = signInUsecase.sigInWithEmailAndPassWord(loginRequest)

        when(result){
            is NetworkResource.Success -> {
                loginValidationAuthEvents.send(LoginViewModel.LoginScreenValidationAndAuthEvent.SuccessfulAuth)
                Log.d("LoginViewModel", "signInUser success => ${result.value} ")
                //save access and refresh tokens
                saveAccessAndRefreshTokens(
                  result.value
                )
            }
            is NetworkResource.Failure -> {
                state = state.copy(showProgressBar = false)
                val errorCode = result.errorCode
                val errorMessage = result.errorBody

                if(errorCode != null && errorMessage != null){
                    loginValidationAuthEvents.send(LoginViewModel.LoginScreenValidationAndAuthEvent.Failure(
                        errorCode = errorCode , errorMessage = errorMessage.byteStream().toString()
                    ))
                }

            }
            else -> {}
        }
    }

    private fun saveAccessAndRefreshTokens(value: LoginResponse) = viewModelScope.launch {
        sessionManager.saveAccessRefreshTokensStartSession(value)
    }


    private fun validateEmail(email : String) : ValidationResults {
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return ValidationResults(errorMessage = "email does not match")
        }
        if (email.isBlank()){
            return ValidationResults(errorMessage = "email should not be empty")
        }
        return ValidationResults(success = true)
    }

    private fun validatePassword(password : String) : ValidationResults {
        if(password.length < 8){
            return ValidationResults(errorMessage = "password should contain more than 8 characters")
        }
        return ValidationResults(success = true)
    }


    sealed class LoginScreenValidationAndAuthEvent {
        object SuccessfulAuth : LoginScreenValidationAndAuthEvent()
        data class Failure(val errorCode : Int? , val errorMessage : String?) : LoginScreenValidationAndAuthEvent()
        object ValidationSuccess : LoginScreenValidationAndAuthEvent()
    }
}