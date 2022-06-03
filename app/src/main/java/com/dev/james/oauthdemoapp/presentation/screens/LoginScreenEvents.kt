package com.dev.james.oauthdemoapp.presentation.screens

import com.dev.james.oauthdemoapp.data.model.LoginResponse

sealed class LoginScreenEvents {
    data class OnEmailFieldTextChange(val email :String) : LoginScreenEvents()
    data class OnPasswordFieldTextChange(val password :String) : LoginScreenEvents()
    object Submit : LoginScreenEvents()
}
