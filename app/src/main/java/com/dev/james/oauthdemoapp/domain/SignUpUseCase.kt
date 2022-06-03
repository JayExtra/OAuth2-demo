package com.dev.james.oauthdemoapp.domain

import com.dev.james.oauthdemoapp.constants.NetworkResource
import com.dev.james.oauthdemoapp.data.model.SignUpRequest
import com.dev.james.oauthdemoapp.data.model.SignUpResponse
import com.dev.james.oauthdemoapp.data.repository.AuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repository : AuthRepository
) {
    suspend fun signUpUser(signUpRequest: SignUpRequest) : NetworkResource<SignUpResponse> =
        repository.signUpUser(signUpRequest)
}