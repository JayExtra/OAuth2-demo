package com.dev.james.oauthdemoapp.presentation.viewmodel

import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.james.oauthdemoapp.constants.NetworkResource
import com.dev.james.oauthdemoapp.data.model.ForgotPasswordBody
import com.dev.james.oauthdemoapp.domain.ForgotPasswordUsecase
import com.dev.james.oauthdemoapp.domain.ValidationResults
import com.dev.james.oauthdemoapp.presentation.screens.events.ForgotPasswordScreenEvents
import com.dev.james.oauthdemoapp.presentation.screens.states.ForgotPasswordScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
   private val forgotPasswordUsecase: ForgotPasswordUsecase
) : ViewModel() {

    var state by mutableStateOf(ForgotPasswordScreenState())

    private val forgotPasswordChannel = Channel<ForgotPasswordEventsChannel>()
    val forgotPasswordEventsChannel = forgotPasswordChannel.receiveAsFlow()

    fun onEvent(event : ForgotPasswordScreenEvents) {
        when(event){
            is ForgotPasswordScreenEvents.OnEmailFieldTextChange ->{
                state = state.copy(email = event.email)
            }
            is ForgotPasswordScreenEvents.Submit ->{
                submitForValidation()
            }
            else -> {}
        }
    }

    private fun submitForValidation() {
        val emailValidationResult = validateEmail(state.email)

        val hasError = listOf(emailValidationResult).any { !it.success }

        state = state.copy(
            emailError = emailValidationResult.errorMessage
        )

        if(hasError){ return }

        viewModelScope.launch {
            forgotPasswordChannel.send(ForgotPasswordEventsChannel.SuccessfulValidation)
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


    fun forgotPassword() = viewModelScope.launch {

        val emailBody = ForgotPasswordBody(email = state.email)
        val result = forgotPasswordUsecase.invoke(emailBody)

        when(result){
            is NetworkResource.Success -> {
                forgotPasswordChannel.send(ForgotPasswordEventsChannel.SentEmail(result.value.message))
            }
            is NetworkResource.Failure -> {
                val errorCode = result.errorCode
                val errorMessage = result.errorBody?.byteStream()
                errorCode?.let {
                    forgotPasswordChannel.send(ForgotPasswordEventsChannel.Failure(errorCode))
                }
            }
            else -> {}
        }
    }


    sealed class ForgotPasswordEventsChannel {
        data class SentEmail(val message : String) : ForgotPasswordEventsChannel()
        data class Failure(val errorCode : Int) : ForgotPasswordEventsChannel()
        object SuccessfulValidation : ForgotPasswordEventsChannel()
    }
}