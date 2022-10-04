package com.dev.james.oauthdemoapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CategoryDiagramsMainResponseDto(
    @SerializedName("data")
    val dataDto: DataDto,
    val links: Any,
    val message: String,
    val meta: Any
)