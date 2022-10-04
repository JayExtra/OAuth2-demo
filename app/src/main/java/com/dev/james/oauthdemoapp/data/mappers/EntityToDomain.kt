package com.dev.james.oauthdemoapp.data.mappers

import com.dev.james.oauthdemoapp.data.local.room.entities.CategoryDiagramEntity
import com.dev.james.oauthdemoapp.data.local.room.entities.PartsEntity
import com.dev.james.oauthdemoapp.domain.models.Categories
import com.dev.james.oauthdemoapp.domain.models.Part

fun CategoryDiagramEntity.toDomain() : Categories {
    return Categories(
        id = id,
        name = name ,
        description = description,
        image = image,
        status = status
    )
}

fun PartsEntity.toDomain() : Part {
    return Part(
        id = id ,
        name = name,
        description = description,
        image = image,
        oemNumber = oemNumber,
        price = price,
        dealerPrice = dealerPrice,
        dealerPricePercentage = dealerPricePercentage,
        categoryId = categoryId,
        isFavourite = isFavourite,
        material = material
    )
}