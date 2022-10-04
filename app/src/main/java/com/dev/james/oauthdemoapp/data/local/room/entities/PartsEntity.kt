package com.dev.james.oauthdemoapp.data.local.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "parts_table")
data class PartsEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("part_id")
    val id : String,
    @ColumnInfo("name")
    val name : String,
    @ColumnInfo("part_description")
    val description : String,
    @ColumnInfo("part_image")
    val image : String,
    @ColumnInfo("part_parent_category_id")
    val categoryId : String,
    @ColumnInfo("part_oem_number")
    val oemNumber : String ,
    @ColumnInfo("part_material")
    val material : String ,
    @ColumnInfo("part_price")
    val price : Int ,
    @ColumnInfo("dealer_price")
    val dealerPrice : Int ,
    @ColumnInfo("dealer_price_percentage")
    val dealerPricePercentage : Int,
    @ColumnInfo("part_favourite_status")
    val isFavourite : Boolean = false
)
