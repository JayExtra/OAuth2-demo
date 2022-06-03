package com.dev.james.oauthdemoapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.dev.james.oauthdemoapp.presentation.screens.destinations.ForgotPasswordScreenDestination
import com.dev.james.oauthdemoapp.presentation.screens.destinations.RegisterScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(start = true)
fun LoginScreen(
    navigator : DestinationsNavigator
) {

    Column (
        horizontalAlignment = Alignment.CenterHorizontally ,
        verticalArrangement = Arrangement.Top ,
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            ) {

        Spacer(modifier = Modifier.height(150.dp))

       Text(
           text = "Login" ,
           modifier = Modifier
               .fillMaxWidth(),
           textAlign = TextAlign.Center ,
           color = MaterialTheme.colors.primary,
           fontSize = 32.sp
       )

        Spacer(modifier = Modifier.height(32.dp))

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
                unfocusedBorderColor = DarkGray
            )
        )

        var password by rememberSaveable(){
            mutableStateOf("")
        }

        OutlinedTextField(
            value = password ,
            onValueChange = {
                password = it
            } ,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, end = 16.dp, start = 16.dp),
            label = {
                Text(
                    text = "Enter password here",
                    color = MaterialTheme.colors.primary
                )
            } ,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.primary,
                unfocusedBorderColor = DarkGray
            )
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Forgot password?",
            textAlign = TextAlign.End ,
            color = MaterialTheme.colors.primary ,
            modifier = Modifier.fillMaxWidth()
                .padding(end = 16.dp)
                .clickable {
                    //navigate
                    navigator.popBackStack()
                    navigator.navigate(ForgotPasswordScreenDestination)
                }
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
                .height(40.dp),
            colors = ButtonDefaults.buttonColors(Blue),
            shape = RoundedCornerShape(6.dp),
            onClick = { /*TODO*/ }) {

            Text(text = "Sign in" , color = White)
        }

        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = buildAnnotatedString {
                val boldStyle = SpanStyle(
                    color = MaterialTheme.colors.primary
                )
                append("Don't have an account?")
                pushStyle(boldStyle)
                append(" Sign up here.")
            },
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp)
                .clickable {
                    // to do
                    navigator.navigate(RegisterScreenDestination)
                },
            color = Black,
        )




    }
}