package com.dev.james.oauthdemoapp.domain

data class ValidationResults(
    val success : Boolean = false ,
    val errorMessage : String? = null
)
