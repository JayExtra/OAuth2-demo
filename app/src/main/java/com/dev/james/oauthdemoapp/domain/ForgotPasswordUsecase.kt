package com.dev.james.oauthdemoapp.domain

import com.dev.james.oauthdemoapp.constants.NetworkResource
import com.dev.james.oauthdemoapp.data.model.ForgotPasswordBody
import com.dev.james.oauthdemoapp.data.model.ForgotPasswordResponse
import com.dev.james.oauthdemoapp.data.repository.AuthRepository
import javax.inject.Inject

class ForgotPasswordUsecase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email : ForgotPasswordBody) : NetworkResource<ForgotPasswordResponse> {
        return authRepository.forgotPassword(email)
    }
}