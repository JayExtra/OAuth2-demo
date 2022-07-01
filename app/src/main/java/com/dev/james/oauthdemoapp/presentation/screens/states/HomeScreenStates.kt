package com.dev.james.oauthdemoapp.presentation.screens.states

import com.dev.james.oauthdemoapp.domain.models.Categories

data class HomeScreenStates(
    val showGridProgressBar : Boolean = false,
    val hideLazyGrid : Boolean = false ,
    val showCardProgressBar : Boolean = false,
    val error : String? = null ,
    val categoryList : List<Categories> = emptyList()
)