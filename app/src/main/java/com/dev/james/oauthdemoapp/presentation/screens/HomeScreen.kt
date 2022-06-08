package com.dev.james.oauthdemoapp.presentation.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.dev.james.oauthdemoapp.presentation.screens.destinations.LoginScreenDestination
import com.dev.james.oauthdemoapp.presentation.viewmodel.HomeViewModel
import com.dev.james.oauthdemoapp.ui.theme.Purple500
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.util.*

@Composable
@Destination
fun HomeScreen(
    navigator : DestinationsNavigator,
    viewModel : HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true){
        viewModel.authSessionFlow.collect { session ->
            //check if access token has expired if we are currently signed in
            Log.d("HomeScreen", "onCreate: access token=> ${session.accessToken}")
            Log.d("HomeScreen", "onCreate: refresh token=> ${session.refreshToken}")

            val date = Calendar.getInstance()
            //Log.d("MainActivity", "onCreate: currently signed in")
            if(viewModel.isSessionExpired(date.time , session.sessionExpiry)){
                //if it has refresh the tokens
                    Log.d("HomeScreen", "onCreate: session has expired fetching new tokens")
                    viewModel.refreshTokens(session.refreshToken)
            }else {
                Log.d("HomeScreen", "onCreate: session is okay")

            }
        }
    }
    Scaffold(
        topBar = {
            HomeScreenTopBar(
                onLogoutClicked = {
                    //trigger logout event from viewmodel
                    Toast.makeText(context, "Signing out...", Toast.LENGTH_LONG).show()
                    viewModel.signOut()
                    navigator.popBackStack()
                    navigator.navigate(LoginScreenDestination)
                }
            )
        } ,
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally ,
                verticalArrangement = Arrangement.Center ,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "Home Screen" ,
                    textAlign = TextAlign.Center ,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}


@Composable
fun HomeScreenTopBar(
    onLogoutClicked : () -> Unit
){

    TopAppBar(
        title = {
            Text(
                text = "OAuth demo" ,
                color = Color.White
            )
        } ,
        backgroundColor = Purple500 ,
        actions = {
            IconButton(onClick = { onLogoutClicked() }) {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = "Log out" )
            }
        }
    )
}