package com.dev.james.oauthdemoapp.presentation.screens.states

import com.dev.james.oauthdemoapp.domain.models.Part

data class CategoryScreenState(
    val partsList : List<Part> = emptyList(),
    val favouritePartsList : List<Part> = emptyList(),
    val fetchError : String? = null,
    val showProgressBar : Boolean = false ,
    val query  : String = "" ,
    val filterParam : String = "",
    val addRemoveFavouriteStatus : Boolean = false ,
    val hasSearched : Boolean = false
)