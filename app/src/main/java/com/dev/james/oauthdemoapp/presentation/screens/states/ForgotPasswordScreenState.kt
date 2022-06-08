package com.dev.james.oauthdemoapp.presentation.screens.states

data class ForgotPasswordScreenState(
    val email : String = "",
    val emailError : String? = null
)