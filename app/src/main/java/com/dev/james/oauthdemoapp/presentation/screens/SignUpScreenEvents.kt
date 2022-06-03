package com.dev.james.oauthdemoapp.presentation.screens

sealed class SignUpScreenEvents {

    data class OnEmailFieldChangeEvent(val email:String) : SignUpScreenEvents()
    data class OnPasswordFieldChangeEvent(val password :String) : SignUpScreenEvents()
    data class OnConfirmPasswordFieldChangeEvent(val confirmPassword :String) : SignUpScreenEvents()
    data class OnPhoneNumberFieldChangeEvent(val phoneNumber:String) : SignUpScreenEvents()

    object SignUpButtonClick : SignUpScreenEvents()
}
