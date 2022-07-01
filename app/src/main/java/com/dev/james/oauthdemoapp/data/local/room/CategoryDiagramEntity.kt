package com.dev.james.oauthdemoapp.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
data class CategoryDiagramEntity(
    @PrimaryKey(autoGenerate = false)
    val id : String ,
    val description : String ,
    val image: String,
    val name: String,
    val status: String
)