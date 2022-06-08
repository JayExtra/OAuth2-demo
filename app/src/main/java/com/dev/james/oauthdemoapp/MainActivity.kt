package com.dev.james.oauthdemoapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavGraph
import com.dev.james.oauthdemoapp.presentation.screens.NavGraphs
import com.dev.james.oauthdemoapp.presentation.viewmodel.MainActivityViewModel
import com.dev.james.oauthdemoapp.ui.theme.OAuthDemoAppTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel : MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

      /**  lifecycleScope.launch {

                viewModel.authSessionFlow.collect { session ->
                        //check if access token has expired if we are currently signed in
                        val date = Calendar.getInstance()
                        //Log.d("MainActivity", "onCreate: currently signed in")

                        if(viewModel.isSessionExpired(date.time , session.sessionExpiry)){
                            //if it has refresh the tokens
                            if (session.refreshToken != "no token" ){
                                Log.d("MainActivity", "onCreate: session has expired fetching new tokens")
                                viewModel.refreshTokens(session.refreshToken)
                            }

                        }else {
                            Log.d("MainActivity", "onCreate: session is okay")

                        }
                }

        }

      **/

        setContent {
            OAuthDemoAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold {
                        DestinationsNavHost(navGraph = NavGraphs.root)
                    }

                }
            }
        }
    }

}