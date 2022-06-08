package com.dev.james.oauthdemoapp.presentation.screens.events

sealed class LoginScreenEvents {
    data class OnEmailFieldTextChange(val email :String) : LoginScreenEvents()
    data class OnPasswordFieldTextChange(val password :String) : LoginScreenEvents()
    object Submit : LoginScreenEvents()
}
