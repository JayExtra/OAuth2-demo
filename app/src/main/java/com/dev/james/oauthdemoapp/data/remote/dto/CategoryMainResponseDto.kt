package com.dev.james.oauthdemoapp.data.remote.dto

import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class CategoryMainResponseDto(
    @SerializedName("data")
    val categoryDataDto: CategoryDataDto,
    val links: Links,
    val message: String,
    val meta: Meta
)