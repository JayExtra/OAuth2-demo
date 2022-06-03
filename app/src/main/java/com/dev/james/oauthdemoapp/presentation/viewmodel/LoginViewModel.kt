package com.dev.james.oauthdemoapp.presentation.viewmodel

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.james.oauthdemoapp.constants.NetworkResource
import com.dev.james.oauthdemoapp.domain.SignInUsecase
import com.dev.james.oauthdemoapp.domain.ValidationResults
import com.dev.james.oauthdemoapp.presentation.screens.LoginScreenEvents
import com.dev.james.oauthdemoapp.presentation.screens.LoginScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInUsecase: SignInUsecase
) : ViewModel() {

    var state by mutableStateOf(LoginScreenState())

    private var loginValidationAuthEvents = Channel<LoginScreenValidationAndAuthEvent>()
    val loginValidationAuthEventsChannel = loginValidationAuthEvents.receiveAsFlow()


    fun onEvent( event : LoginScreenEvents) {
        when(event){
            is LoginScreenEvents.OnEmailFieldTextChange ->{
                state = state.copy(email = event.email)
            }
            is LoginScreenEvents.OnPasswordFieldTextChange ->{
                state = state.copy(password = event.password)
            }
            is LoginScreenEvents.Submit ->{
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
            emailError = emailValidationResults.errorMessage
        )

        if(hasError){ return }

        viewModelScope.launch {
            loginValidationAuthEvents.send(LoginViewModel.LoginScreenValidationAndAuthEvent.ValidationSuccess)
        }
    }

    fun signInUser() = viewModelScope.launch {
        val result = signInUsecase.sigInWithEmailAndPassWord(state.email , state.password)

        when(result){
            is NetworkResource.Success -> {
                loginValidationAuthEvents.send(LoginViewModel.LoginScreenValidationAndAuthEvent.SuccessfulAuth)
            }
            is NetworkResource.Failure -> {
                val error = result.errorCode
                val errorMessage = result.errorBody?.byteStream().toString()

                loginValidationAuthEvents.send(LoginViewModel.LoginScreenValidationAndAuthEvent.Failure(
                    errorCode = error , errorMessage = errorMessage
                ))
            }
        }
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