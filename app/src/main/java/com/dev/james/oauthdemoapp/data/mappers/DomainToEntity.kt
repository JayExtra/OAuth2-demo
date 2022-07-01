package com.dev.james.oauthdemoapp.data.mappers

import com.dev.james.oauthdemoapp.data.local.room.CategoryDiagramEntity
import com.dev.james.oauthdemoapp.domain.models.Categories

fun Categories.toEntity() : CategoryDiagramEntity {
    return CategoryDiagramEntity(
        name = name ,
        id = id,
        image = image ,
        description = description,
        status = status
    )
}