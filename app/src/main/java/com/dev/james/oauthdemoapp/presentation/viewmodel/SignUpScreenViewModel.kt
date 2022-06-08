package com.dev.james.oauthdemoapp.presentation.viewmodel

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.james.oauthdemoapp.constants.NetworkResource
import com.dev.james.oauthdemoapp.data.model.SignUpRequest
import com.dev.james.oauthdemoapp.domain.SignUpUseCase
import com.dev.james.oauthdemoapp.domain.ValidationResults
import com.dev.james.oauthdemoapp.presentation.screens.events.SignUpScreenEvents
import com.dev.james.oauthdemoapp.presentation.screens.states.SignUpScreenStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpScreenViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {


    var state by mutableStateOf(SignUpScreenStates())

    private val validationAndAuthEventChannel = Channel<ValidationAndAuthentificationEvent>()
    val validationAndAuthEvents = validationAndAuthEventChannel.receiveAsFlow()


    fun onEvent(event : SignUpScreenEvents) {
        when(event) {
            is SignUpScreenEvents.OnEmailFieldChangeEvent -> {
                state = state.copy(email = event.email)
            }
            is SignUpScreenEvents.OnPasswordFieldChangeEvent -> {
                state = state.copy(password = event.password)
            }
            is SignUpScreenEvents.OnConfirmPasswordFieldChangeEvent -> {
                state = state.copy(confirmPassword = event.confirmPassword)
            }
            is SignUpScreenEvents.OnPhoneNumberFieldChangeEvent -> {
                state = state.copy(phoneNumber = event.phoneNumber )
            }
            is SignUpScreenEvents.SignUpButtonClick -> {
                submitForValidation()
            }
        }
    }

    private fun submitForValidation() {
        val emailValidationResult = validateEmail(state.email)
        val passwordValidationResult = validatePassword(state.password)
        val confirmPasswordValidationResult = validateConfirmPassword(state.password , state.confirmPassword)
        val phoneNumberValidationResult = validatePhoneNumber(state.phoneNumber)

        val hasError = listOf(
            emailValidationResult ,
            passwordValidationResult ,
            confirmPasswordValidationResult,
            phoneNumberValidationResult
        ).any {
            !it.success
        }

        state = state.copy(
            emailError = emailValidationResult.errorMessage,
            passwordError = passwordValidationResult.errorMessage,
            confirmPasswordError = confirmPasswordValidationResult.errorMessage,
            phoneNumberError = phoneNumberValidationResult.errorMessage,
        )

        if (hasError){ return }

        viewModelScope.launch {
            validationAndAuthEventChannel.send(ValidationAndAuthentificationEvent.Success)
        }
    }

    fun signUpUser() = viewModelScope.launch {
        val signUpRequest = SignUpRequest(
            name = "John Doe",
            email = state.email ,
            password = state.password ,
            confirmPassword = state.confirmPassword ,
            phoneNumber = state.phoneNumber,
            countryCode = "254"
        )

        Log.d("SignUpViewModel", "signUpUser: $signUpRequest")

        val result = signUpUseCase.signUpUser(signUpRequest)

        when(result){
            is NetworkResource.Success -> {
                Log.d("SignUpViewModel", "signUpUserSuccess: ${result.value.message} ")
              validationAndAuthEventChannel.send(ValidationAndAuthentificationEvent.SuccessAuth(result.value.message))
            }
            is NetworkResource.Failure -> {
                Log.d("SignUpViewModel", "signUpUserFailure: ${result.errorBody?.byteStream().toString()} ")

                if(result.errorBody != null && result.errorCode != null){
                    val errorMessage = result.errorBody.toString()

                    validationAndAuthEventChannel.send(
                        ValidationAndAuthentificationEvent.Failure(
                            errorCode = result.errorCode,
                            errorMessage = errorMessage
                        )
                    )
                }
            }
            is NetworkResource.Loading -> {
                state = state.copy(showProgressBar = true)
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
    private fun validateConfirmPassword(password: String ,confirmPassword : String) : ValidationResults {
        if(password != confirmPassword){
            return ValidationResults(errorMessage = "passwords do not match")
        }
        return ValidationResults(success = true)
    }
    private fun validatePhoneNumber(phoneNumber : String) : ValidationResults {
        if(phoneNumber.isBlank()){
            return ValidationResults(errorMessage = "please provide phone number")
        }
        if (!phoneNumber.trim().isDigitsOnly()){
            return ValidationResults(errorMessage = "invalid phone number")
        }
        return ValidationResults(success = true)
    }

    sealed class ValidationAndAuthentificationEvent {
        data class SuccessAuth(val message : String) : ValidationAndAuthentificationEvent()
        data class Failure(val errorCode : Int? , val errorMessage : String?) : ValidationAndAuthentificationEvent()
        object Success : ValidationAndAuthentificationEvent()
    }

}