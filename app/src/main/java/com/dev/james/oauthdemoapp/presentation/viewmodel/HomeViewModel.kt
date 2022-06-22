package com.dev.james.oauthdemoapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.james.oauthdemoapp.data.model.RefreshTokenBody
import com.dev.james.oauthdemoapp.domain.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val sessionManager : SessionManager
) : ViewModel() {

    init {
        viewModelScope.launch {
            delay(3000)
            val accessToken = sessionManager.getAccessToken()
            val refreshToken = sessionManager.getRefreshToken()
            accessToken?.let {
                Log.d("HomeViewModel", "access token : $it ")

            }?:  Log.d("HomeViewModel", "access token : $accessToken ")
            refreshToken?.let {
                Log.d("HomeViewModel", "refresh token : $it ")

            } ?: Log.d("HomeViewModel", "refresh token : $refreshToken ")
        }
    }
    val authSessionFlow = sessionManager.fetchAuthSessionPreferences()

    fun isSessionExpired(
        currentTime : Date,
        expiryDate : Long
    ) : Boolean {
        return sessionManager.isSessionExpired(currentTime , expiryDate)
    }

    fun refreshTokens(refreshToken : String ) = viewModelScope.launch {
        val refreshTokenBody = RefreshTokenBody(refreshToken = refreshToken)
        sessionManager.refreshTokens(refreshTokenBody)
    }

    fun signOut() = runBlocking {
        sessionManager.saveUserSignInStatus(false)
        sessionManager.clearTokens()
    }
}