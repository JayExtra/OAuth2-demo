package com.dev.james.oauthdemoapp.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dev.james.oauthdemoapp.presentation.screens.destinations.ForgotPasswordScreenDestination
import com.dev.james.oauthdemoapp.presentation.screens.destinations.HomeScreenDestination
import com.dev.james.oauthdemoapp.presentation.screens.destinations.RegisterScreenDestination
import com.dev.james.oauthdemoapp.presentation.screens.events.LoginScreenEvents
import com.dev.james.oauthdemoapp.presentation.viewmodel.LoginViewModel
import com.dev.james.oauthdemoapp.ui.theme.Purple500
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination()
fun LoginScreen(
    navigator : DestinationsNavigator,
    viewModel : LoginViewModel = hiltViewModel()
) {

    val state = viewModel.state
    val context = LocalContext.current
    LaunchedEffect(key1 = context ) {
        viewModel.loginValidationAuthEventsChannel.collect { events ->
            when(events){
                is LoginViewModel.LoginScreenValidationAndAuthEvent.ValidationSuccess -> {
                    viewModel.signInUser()
                    Toast.makeText(context, "Signing in...", Toast.LENGTH_LONG).show()
                }
                is LoginViewModel.LoginScreenValidationAndAuthEvent.SuccessfulAuth -> {
                    Toast.makeText(context , "successful sign in" , Toast.LENGTH_LONG).show()
                    navigator.popBackStack()
                    navigator.navigate(HomeScreenDestination)
                }
                is LoginViewModel.LoginScreenValidationAndAuthEvent.Failure -> {
                    val errorCode = events.errorCode
                    val errorBody = events.errorMessage
                    Toast.makeText(context , "Error $errorCode : $errorBody" , Toast.LENGTH_LONG).show()
                }
            }


        }
    }


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

        OutlinedTextField(
            value = state.email ,
            onValueChange = {
               viewModel.onEvent(LoginScreenEvents.OnEmailFieldTextChange(it))
            } ,
            isError = state.emailError != null,
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
                unfocusedBorderColor = DarkGray ,
                textColor = Black
            )
        )
        if(state.emailError != null){
            Text(
                text = state.emailError ,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp)
            )
        }


        OutlinedTextField(
            value = state.password ,
            onValueChange = {
               viewModel.onEvent(LoginScreenEvents.OnPasswordFieldTextChange(it))
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
                unfocusedBorderColor = DarkGray,
                textColor = Black
            )
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Forgot password?",
            textAlign = TextAlign.End ,
            color = MaterialTheme.colors.primary ,
            modifier = Modifier
                .fillMaxWidth()
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
            onClick = {
                viewModel.onEvent(LoginScreenEvents.Submit)

            }) {

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

        if(state.showProgressBar){
            CircularProgressIndicator(
                color = Purple500 ,
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
            )
        }




    }
}