package com.dev.james.oauthdemoapp.domain

import com.dev.james.oauthdemoapp.data.repository.AuthRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCategoriesUsecase @Inject constructor(
    private val repository: AuthRepository
) {
   fun execute() = repository.getCategoriesList()
}