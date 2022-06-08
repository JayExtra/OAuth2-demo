package com.dev.james.oauthdemoapp.presentation.screens.states

data class SignUpScreenStates(
    val email : String = "" ,
    val emailError : String? = null ,
    val password : String = "" ,
    val passwordError : String? = null ,
    val confirmPassword : String = "" ,
    val confirmPasswordError : String? = null ,
    val phoneNumber : String = "" ,
    val phoneNumberError : String? = null ,
    val showProgressBar : Boolean = false
)
