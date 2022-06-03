package com.dev.james.oauthdemoapp.presentation.screens

data class LoginScreenState(
    val email : String = "" ,
    val emailError : String? = null ,
    val password : String = "" ,
    val passwordError : String? = null
)
