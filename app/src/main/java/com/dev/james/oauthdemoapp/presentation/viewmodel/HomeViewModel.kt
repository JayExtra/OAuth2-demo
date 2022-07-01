package com.dev.james.oauthdemoapp.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.james.oauthdemoapp.constants.Resource
import com.dev.james.oauthdemoapp.data.model.RefreshTokenBody
import com.dev.james.oauthdemoapp.domain.GetCategoriesUsecase
import com.dev.james.oauthdemoapp.domain.models.Categories
import com.dev.james.oauthdemoapp.domain.session.SessionManager
import com.dev.james.oauthdemoapp.presentation.screens.states.HomeScreenStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val sessionManager : SessionManager ,
    private val categoriesUsecase: GetCategoriesUsecase
) : ViewModel() {

    var homeScreenState by mutableStateOf(HomeScreenStates())
    val categoryList = categoriesUsecase.execute()

    init {
        homeScreenState = homeScreenState.copy(showGridProgressBar = true)
    }

    fun updateScreenState(data: List<Categories>?) {
        homeScreenState = homeScreenState.copy(showGridProgressBar = false)
        data?.let { categories ->
            homeScreenState = homeScreenState.copy(categoryList = categories)
        }
    }

    fun updateErrorState(message: String?, data: List<Categories>?){
        homeScreenState = homeScreenState.copy(showGridProgressBar = false)
        message?.let {
            homeScreenState = homeScreenState.copy(error = it)
        }
        data?.let {
            homeScreenState = homeScreenState.copy(categoryList = it)
        }

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