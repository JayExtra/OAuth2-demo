package com.dev.james.oauthdemoapp.data.remote.dto

import com.google.gson.annotations.SerializedName


data class CategoryDiagramDto(
    val description: String,
    val id: String,
    val image: String,
    val name: String,
    @SerializedName("parts")
    val partDto: List<PartDto>,
    val status: String
)