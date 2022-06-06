package com.dev.james.oauthdemoapp.data.local.datastore

import androidx.datastore.preferences.core.*


object PrefKeysManager {
    val STORE_NAME = "com.dev.james.oauthdemoapp.oauth_datastore"

    //store our access and refresh tokens
    val REFRESH_TOKEN : Preferences.Key<String> =
        stringPreferencesKey("refresh_token")

    val ACCESS_TOKEN : Preferences.Key<String> =
        stringPreferencesKey("access_token")

    val SESSION_EXPIRY_TIME : Preferences.Key<Long> =
        longPreferencesKey("session_expiry_time")

    val REFRESH_TOKEN_EXPIRY_TIME : Preferences.Key<Long> =
        longPreferencesKey("refresh_token_expiry_time")

    val IS_USER_SIGNED_IN : Preferences.Key<Boolean> =
        booleanPreferencesKey("is_user_signed_in")


    const val DEFAULT_KEY = "my default key"

}