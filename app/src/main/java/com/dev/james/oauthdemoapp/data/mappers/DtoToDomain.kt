package com.dev.james.oauthdemoapp.data.mappers

import com.dev.james.oauthdemoapp.data.remote.dto.CategoryDiagramDto
import com.dev.james.oauthdemoapp.data.remote.dto.PartsMainResponseDto
import com.dev.james.oauthdemoapp.domain.models.Categories
import com.dev.james.oauthdemoapp.domain.models.CategoryDiagrams


fun PartsMainResponseDto.toDomain() : CategoryDiagrams {
    return CategoryDiagrams(
        categoriesList = dataDto.categoryDiagramDto.map { it.toDomain() }
    )
}

fun CategoryDiagramDto.toDomain() : Categories {
    return Categories(
        name = name ,
        description = description ,
        id = id,
        image = image,
        status = status
    )
}