package com.dev.james.oauthdemoapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.james.oauthdemoapp.data.model.RefreshTokenBody
import com.dev.james.oauthdemoapp.domain.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val sessionManager : SessionManager
) : ViewModel() {
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

    fun signOut() = viewModelScope.launch {
        sessionManager.saveUserSignInStatus(false)
        sessionManager.clearTokens()
    }
}