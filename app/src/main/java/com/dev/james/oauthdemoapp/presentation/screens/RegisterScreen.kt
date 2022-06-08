package com.dev.james.oauthdemoapp.presentation.screens


import android.util.Log
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
import androidx.compose.ui.graphics.Color
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
import com.dev.james.oauthdemoapp.presentation.screens.destinations.LoginScreenDestination
import com.dev.james.oauthdemoapp.presentation.screens.events.SignUpScreenEvents
import com.dev.james.oauthdemoapp.presentation.viewmodel.SignUpScreenViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun RegisterScreen(
    navigator : DestinationsNavigator ,
    viewModel : SignUpScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(key1 = context){
        viewModel.validationAndAuthEvents.collect { validationEvent ->
            when(validationEvent){
                is SignUpScreenViewModel.ValidationAndAuthentificationEvent.Success -> {
                    Toast.makeText(context, "validation okay ", Toast.LENGTH_SHORT).show()
                    viewModel.signUpUser()
                }
                is SignUpScreenViewModel.ValidationAndAuthentificationEvent.SuccessAuth -> {
                    Log.d("RegisterScreen", "authEvent: ${validationEvent.message} ")
                    Toast.makeText(context, validationEvent.message, Toast.LENGTH_SHORT).show()

                    Toast.makeText(context , "A verification email has been sent to your email. Please check."
                        ,Toast.LENGTH_LONG).show()
                    navigator.popBackStack()
                    navigator.navigate(LoginScreenDestination)
                }
                is SignUpScreenViewModel.ValidationAndAuthentificationEvent.Failure -> {
                    Log.d("RegisterScreen", "authEvent Error: ${validationEvent.errorCode} : ${validationEvent.errorMessage} ")

                    Toast.makeText(context, validationEvent.errorMessage + validationEvent.errorCode, Toast.LENGTH_SHORT).show()

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

        Spacer(modifier = Modifier.height(34.dp))

        Text(
            text = "Sign up",
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
                viewModel.onEvent(SignUpScreenEvents.OnEmailFieldChangeEvent(it))
            } ,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, end = 16.dp, start = 16.dp),
            isError = state.emailError != null,
            label = {
                Text(
                    text = "Enter email here",
                    color = Black
                )
            } ,

            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.primary,
                unfocusedBorderColor = DarkGray,
                textColor = Color.Black
            )
        )
        if(state.emailError != null){
            Text(
                text = state.emailError ,
                color = MaterialTheme.colors.error,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 8.dp))
        }


        OutlinedTextField(
            value = state.password ,
            onValueChange = {
                viewModel.onEvent(SignUpScreenEvents.OnPasswordFieldChangeEvent(it))
            } ,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, end = 16.dp, start = 16.dp),
            label = {
                Text(
                    text = "Enter password here",
                    color = Black
                )
            } ,
            isError = state.passwordError != null,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.primary,
                unfocusedBorderColor = DarkGray ,
                textColor = Color.Black
            )
        )
        if(state.passwordError != null){
            Text(text = state.passwordError ,
                color = MaterialTheme.colors.error ,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 8.dp)
            )
        }


        OutlinedTextField(
            value = state.confirmPassword ,
            onValueChange = {
                viewModel.onEvent(SignUpScreenEvents.OnConfirmPasswordFieldChangeEvent(it))
            } ,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, end = 16.dp, start = 16.dp),
            label = {
                Text(
                    text = "Confirm password",
                    color = Black
                )
            } ,
            isError = state.confirmPasswordError != null ,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.primary,
                unfocusedBorderColor = DarkGray,
                textColor = Color.Black
            )
        )
        if(state.confirmPasswordError != null){
            Text(
                text = state.confirmPasswordError ,
                color = MaterialTheme.colors.error,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 8.dp)
            )
        }


        OutlinedTextField(
            value = state.phoneNumber ,
            onValueChange = {
                viewModel.onEvent(SignUpScreenEvents.OnPhoneNumberFieldChangeEvent(it))
            } ,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, end = 16.dp, start = 16.dp),
            label = {
                Text(
                    text = "Enter phone number",
                    color = Black
                )
            } ,
            isError = state.phoneNumberError != null ,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.primary,
                unfocusedBorderColor = DarkGray,
                textColor = Color.Black
            )
        )
        if(state.phoneNumberError != null){
            Text(
                text = state.phoneNumberError ,
                color = MaterialTheme.colors.error,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 8.dp)
            )
        }


        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
                .height(40.dp),
            colors = ButtonDefaults.buttonColors(Blue),
            shape = RoundedCornerShape(6.dp),
            onClick = {
                viewModel.onEvent(SignUpScreenEvents.SignUpButtonClick)
            }) {

            Text(text = "Sign up" , color = White)
        }

        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = buildAnnotatedString {
                val boldStyle = SpanStyle(
                    color = MaterialTheme.colors.primary
                )
                append("Already have an account?")
                pushStyle(boldStyle)
                append(" Sign in here.")
            },
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp)
                .clickable {
                    // to do
                    navigator.popBackStack()
                    navigator.navigate(LoginScreenDestination)
                },
            color = Black,
        )




    }
}