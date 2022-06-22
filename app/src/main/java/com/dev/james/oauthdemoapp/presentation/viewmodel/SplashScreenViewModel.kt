package com.dev.james.oauthdemoapp.presentation.viewmodel


import androidx.lifecycle.ViewModel
import com.dev.james.oauthdemoapp.domain.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {

    fun checkAuthState() : Flow<Boolean> {
       return sessionManager.readUserSignedInStatus()
    }
}