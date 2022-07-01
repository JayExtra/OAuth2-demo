package com.dev.james.oauthdemoapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class DataDto(
    @SerializedName("diagrams")
    val categoryDiagramDto: List<CategoryDiagramDto>
)