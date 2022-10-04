package com.dev.james.oauthdemoapp.domain

import com.dev.james.oauthdemoapp.constants.Resource
import com.dev.james.oauthdemoapp.data.repository.AuthRepository
import com.dev.james.oauthdemoapp.domain.models.Part
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchPartsUsecase @Inject constructor(
   private val authRepository: AuthRepository
) {
    fun execute(categoryId : String) : Flow<Resource<List<Part>>> {
     return authRepository.getCategoryParts(categoryId)
    }


    fun getFavouriteParts() : Flow<List<Part>> {
        return authRepository.getFavouritesList()
    }


    fun searchForPart(query : String) : Flow<List<Part>>{
        return authRepository.searchPart(query)
    }

}