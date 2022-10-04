package com.dev.james.oauthdemoapp.domain.models

data class Part(
    val id : String,
    val name : String,
    val description : String,
    val image : String,
    val categoryId : String,
    val oemNumber : String ,
    val material : String ,
    val price : Int ,
    val dealerPrice : Int ,
    val dealerPricePercentage : Int,
    val isFavourite : Boolean = false
)