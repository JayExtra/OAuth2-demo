package com.dev.james.oauthdemoapp.data.mappers

import com.dev.james.oauthdemoapp.data.local.room.CategoryDiagramEntity
import com.dev.james.oauthdemoapp.domain.models.Categories

fun CategoryDiagramEntity.toDomain() : Categories {
    return Categories(
        id = id,
        name = name ,
        description = description,
        image = image,
        status = status
    )
}