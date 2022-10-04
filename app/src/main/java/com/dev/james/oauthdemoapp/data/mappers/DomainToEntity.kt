package com.dev.james.oauthdemoapp.data.mappers

import com.dev.james.oauthdemoapp.data.local.room.entities.CategoryDiagramEntity
import com.dev.james.oauthdemoapp.data.local.room.entities.PartsEntity
import com.dev.james.oauthdemoapp.domain.models.Categories
import com.dev.james.oauthdemoapp.domain.models.Part

fun Categories.toEntity() : CategoryDiagramEntity {
    return CategoryDiagramEntity(
        name = name ,
        id = id,
        image = image ,
        description = description,
        status = status
    )
}

fun Part.toEntity() : PartsEntity {
    return PartsEntity(
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