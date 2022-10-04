package com.dev.james.oauthdemoapp.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Categories(
    val id : String ,
    val image: String,
    val name: String,
    val status: String ,
    val description : String
) : Parcelable