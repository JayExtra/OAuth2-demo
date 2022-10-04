package com.dev.james.oauthdemoapp.data.remote.dto

data class CategoryDataDto(
    val description: String,
    val id: String,
    val image: String,
    val name: String,
    val parts: List<PartDto>,
    val status: String
)