package com.dev.james.oauthdemoapp.data.local.datastore

data class AllSessionPreferences(
    val accessToken : String ,
    val refreshToken : String,
    val sessionExpiry : Long ,
    val refreshTokenExpiry : Long,
    val signInStatus : Boolean
)