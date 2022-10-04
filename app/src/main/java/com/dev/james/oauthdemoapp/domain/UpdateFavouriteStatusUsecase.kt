package com.dev.james.oauthdemoapp.domain

import com.dev.james.oauthdemoapp.data.repository.AuthRepository
import javax.inject.Inject

class UpdateFavouriteStatusUsecase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun execute(value : Boolean , partId : String){
        authRepository.setIsFavourite(value, partId)
    }
}