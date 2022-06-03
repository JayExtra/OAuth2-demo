package com.dev.james.oauthdemoapp.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.dev.james.oauthdemoapp.ui.theme.Purple500
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun HomeScreen() {
    Scaffold(
        topBar = {
            HomeScreenTopBar(
                onLogoutClicked = {
                    //trigger logout event from viewmodel
                }
            )
        } ,
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally ,
                verticalArrangement = Arrangement.Center
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
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = "Log out" )

                Text(text = "Logout" , color = Color.White )
            }
        }
    )
}