package com.dev.james.oauthdemoapp.presentation.screens

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dev.james.oauthdemoapp.presentation.screens.destinations.LoginScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun ForgotPasswordScreen(
    navigator : DestinationsNavigator
){

    Column(
        horizontalAlignment = Alignment.CenterHorizontally ,
        verticalArrangement = Arrangement.Top ,
        modifier = Modifier.fillMaxSize()
            .background(Color.White)
    ) {
        Spacer(modifier = Modifier.height(150.dp))

        Text(
            text = "Forgot Password" ,
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center ,
            color = MaterialTheme.colors.primary,
            fontSize = 32.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = buildAnnotatedString {
                val boldStyle = SpanStyle(
                    color = MaterialTheme.colors.primary
                )
                append("Forgotten your password? Please provide your email below and we shall send you a password reset email. If not then login")
                pushStyle(boldStyle)
                append(" here.")
            },
            fontSize = 18.sp ,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp , start = 16.dp)
                .clickable {
                    // to do
                           navigator.popBackStack()
                    navigator.navigate(LoginScreenDestination)
                },
            color = Color.Black

        )

        Spacer(modifier = Modifier.height(20.dp))

        var email by rememberSaveable(){
            mutableStateOf("")
        }

        OutlinedTextField(
            value = email ,
            onValueChange = {
                email = it
            } ,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, end = 16.dp, start = 16.dp),
            label = {
                Text(
                    text = "Enter email here",
                    color = MaterialTheme.colors.primary
                )
            } ,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.primary,
                unfocusedBorderColor = Color.DarkGray
            )
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
                .height(40.dp),
            colors = ButtonDefaults.buttonColors(Color.Blue),
            shape = RoundedCornerShape(6.dp),
            onClick = { /*TODO*/ }) {

            Text(text = "Send reset email" , color = Color.White)
        }

    }
}