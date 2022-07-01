package com.dev.james.oauthdemoapp.data.model

import com.dev.james.oauthdemoapp.data.remote.dto.PartDto
import com.google.gson.annotations.SerializedName

data class CategoryDiagramDomain(
    val description: String,
    val id: String,
    val image: String,
    val name: String,
    val status: String
)