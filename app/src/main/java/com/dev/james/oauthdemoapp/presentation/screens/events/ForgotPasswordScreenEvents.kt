package com.dev.james.oauthdemoapp.presentation.screens.events

sealed class ForgotPasswordScreenEvents {
    data class OnEmailFieldTextChange(val email : String) : ForgotPasswordScreenEvents()
    object Submit : ForgotPasswordScreenEvents()
}