package com.dev.james.oauthdemoapp.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.james.oauthdemoapp.domain.FetchPartsUsecase
import com.dev.james.oauthdemoapp.presentation.screens.states.FavouritesScreenStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val fetchPartsUsecase: FetchPartsUsecase
) : ViewModel() {
    private var job : Job? = null

    var favScreenState by mutableStateOf(FavouritesScreenStates())

    init {
        getFavourites()
    }

    private fun getFavourites() {
        job?.cancel()
        job = fetchPartsUsecase.getFavouriteParts().onEach { part ->
            favScreenState = favScreenState.copy(favPartsList = part)
        }.launchIn(viewModelScope)
    }

    fun toggleFavourite(id : String) {

        val parts = favScreenState.favPartsList.toMutableList()
        val itemIndex = parts.indexOfFirst { it.id == id }
        val part = parts[itemIndex]
        parts[itemIndex] = part.copy(isFavourite = !part.isFavourite)

        favScreenState = favScreenState.copy(favPartsList = parts)

    }
}