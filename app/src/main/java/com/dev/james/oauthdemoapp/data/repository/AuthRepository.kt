package com.dev.james.oauthdemoapp.data.repository

import com.dev.james.oauthdemoapp.constants.NetworkResource
import com.dev.james.oauthdemoapp.data.model.LoginResponse
import com.dev.james.oauthdemoapp.data.model.SignUpRequest
import com.dev.james.oauthdemoapp.data.model.SignUpResponse
import com.dev.james.oauthdemoapp.data.remote.AuthApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: AuthApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseRepositoryApiCall() {
    suspend fun signUpUser(signUpRequest: SignUpRequest) : NetworkResource<SignUpResponse> = safeApiCall {
        api.signUp(signUpRequest)
    }

    suspend fun signInUser(email : String , password : String) : NetworkResource<LoginResponse> = safeApiCall {
        api.signInUser(email = email , password = password)
    }
}