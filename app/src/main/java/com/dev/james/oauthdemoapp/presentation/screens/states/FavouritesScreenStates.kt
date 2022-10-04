package com.dev.james.oauthdemoapp.presentation.screens.states

import com.dev.james.oauthdemoapp.domain.models.Part

data class FavouritesScreenStates(
    val favPartsList : List<Part> = emptyList(),
    val addRemoveFav : Boolean = false,
    val isLoading : Boolean = false
)