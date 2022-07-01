package com.dev.james.oauthdemoapp.presentation.screens.events

import com.dev.james.oauthdemoapp.presentation.screens.states.HomeScreenStates

sealed class HomeScreenEvents{
    data class NavigateToCategoryDetails(val catId : String) : HomeScreenEvents()
}