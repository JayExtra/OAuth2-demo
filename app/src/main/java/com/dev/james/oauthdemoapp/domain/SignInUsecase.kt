package com.dev.james.oauthdemoapp.domain

import com.dev.james.oauthdemoapp.constants.NetworkResource
import com.dev.james.oauthdemoapp.data.model.LoginRequest
import com.dev.james.oauthdemoapp.data.model.LoginResponse
import com.dev.james.oauthdemoapp.data.repository.AuthRepository
import javax.inject.Inject

class SignInUsecase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend fun sigInWithEmailAndPassWord(
        loginRequest: LoginRequest
    ) : NetworkResource<LoginResponse>  =
        repository.signInUser(loginRequest)
}