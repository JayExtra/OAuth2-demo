package com.dev.james.oauthdemoapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dev.james.oauthdemoapp.presentation.screens.destinations.HomeScreenDestination
import com.dev.james.oauthdemoapp.presentation.screens.destinations.LoginScreenDestination
import com.dev.james.oauthdemoapp.presentation.viewmodel.SplashScreenViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay


@Composable
@Destination(start = true)
fun SplashScreen(
    navigator : DestinationsNavigator,
    viewModel: SplashScreenViewModel = hiltViewModel()
){

    val context = LocalContext.current
    LaunchedEffect(key1 = context){
        delay(2000)
        viewModel.checkAuthState().collect{ state ->
            if(state){
                navigator.popBackStack()
                navigator.navigate(HomeScreenDestination)
            }else{
                navigator.popBackStack()
                navigator.navigate(LoginScreenDestination)
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally ,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
            .background(Color.White),
    ) {
        Text(text = "OAuth demo" ,
        modifier = Modifier.fillMaxWidth() ,
        fontSize = 32.sp ,
        color = Color.Blue ,
        textAlign = TextAlign.Center)
    }
}