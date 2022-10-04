package com.dev.james.oauthdemoapp.data.mappers

import com.dev.james.oauthdemoapp.data.remote.dto.CategoryDiagramDto
import com.dev.james.oauthdemoapp.data.remote.dto.CategoryDiagramsMainResponseDto
import com.dev.james.oauthdemoapp.data.remote.dto.PartDto
import com.dev.james.oauthdemoapp.domain.models.Categories
import com.dev.james.oauthdemoapp.domain.models.CategoryDiagrams
import com.dev.james.oauthdemoapp.domain.models.Part
import okhttp3.MultipartBody


fun CategoryDiagramsMainResponseDto.toDomain() : CategoryDiagrams {
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

fun PartDto.toDomain(categoryId : String) : Part {
    return Part(
        id = id,
        name = name,
        description = description,
        image = imageDto[0].path,
        categoryId = categoryId,
        oemNumber = oem_number,
        material = material,
        price = price,
        dealerPrice = dealer_price,
        dealerPricePercentage = dealer_price_percentage
    )

}