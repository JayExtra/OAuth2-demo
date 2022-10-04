package com.dev.james.oauthdemoapp.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.james.oauthdemoapp.domain.models.Part
import com.dev.james.oauthdemoapp.domain.FetchPartsUsecase
import com.dev.james.oauthdemoapp.domain.UpdateFavouriteStatusUsecase
import com.dev.james.oauthdemoapp.presentation.screens.states.CategoryScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryScreenViewModel @Inject constructor(
    private val fetchPartsUsecase: FetchPartsUsecase ,
    private val updateFavouriteStatusUsecase: UpdateFavouriteStatusUsecase
): ViewModel() {

    var categoryScreenState by mutableStateOf(CategoryScreenState())

    private var getFavouritesJob : Job? = null
    private var searchJob : Job? = null



    init {
        categoryScreenState = categoryScreenState.copy(showProgressBar = true)
        getFavourites()
    }

    fun fetchParts(categoryId : String) = fetchPartsUsecase.execute(categoryId)

    fun updateScreenState(partsList : List<Part>?){
        Log.d("CategoryScreenVm", "getPartsList data: $partsList")
        categoryScreenState = categoryScreenState.copy(showProgressBar = false)
        partsList?.let {
            categoryScreenState = categoryScreenState.copy(partsList = it)
        }
    }

    private fun setIsFavourite(value : Boolean , partId : String) = viewModelScope.launch{
        updateFavouriteStatusUsecase.execute(value, partId)
    }

    fun updateErrorState( message : String? , data : List<Part>? ){
        categoryScreenState = categoryScreenState.copy(showProgressBar = false)

        categoryScreenState = categoryScreenState.copy(fetchError = message )

        data?.let {
            categoryScreenState = categoryScreenState.copy(partsList = it)
        }
    }

    fun toggleFavourite(id : String) {

        val parts = categoryScreenState.partsList.toMutableList()
        val itemIndex = parts.indexOfFirst { it.id == id }
        val part = parts[itemIndex]
        parts[itemIndex] = part.copy(isFavourite = !part.isFavourite)


        categoryScreenState = categoryScreenState.copy(partsList = parts)
        setIsFavourite(!part.isFavourite , part.id)

    }

    private fun getFavourites() {
        getFavouritesJob?.cancel()
        getFavouritesJob = fetchPartsUsecase.getFavouriteParts()
            .onEach { parts ->
                categoryScreenState = categoryScreenState.copy(
                    favouritePartsList = parts
                )

            }.launchIn(viewModelScope)
    }

     fun searchFavourite(query : String) {
        categoryScreenState = categoryScreenState.copy(
            hasSearched = true
        )
        searchJob?.cancel()
        searchJob = fetchPartsUsecase.searchForPart(query)
            .onEach { parts ->
                if(parts.isEmpty()){
                    categoryScreenState = categoryScreenState.copy(
                        fetchError = "search result not found"
                    )
                    categoryScreenState = categoryScreenState.copy(
                        partsList = parts
                    )
                }else{
                    categoryScreenState = categoryScreenState.copy(
                        partsList = parts
                    )
                    categoryScreenState = categoryScreenState.copy(
                        hasSearched = false
                    )
                }

            }.launchIn(viewModelScope)
    }

}