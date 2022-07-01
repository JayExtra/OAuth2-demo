package com.dev.james.oauthdemoapp.presentation.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.dev.james.oauthdemoapp.R
import com.dev.james.oauthdemoapp.constants.Resource
import com.dev.james.oauthdemoapp.domain.models.Categories
import com.dev.james.oauthdemoapp.presentation.screens.destinations.LoginScreenDestination
import com.dev.james.oauthdemoapp.presentation.screens.states.HomeScreenStates
import com.dev.james.oauthdemoapp.presentation.viewmodel.HomeViewModel
import com.dev.james.oauthdemoapp.ui.theme.Purple500
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Destination
fun HomeScreen(
    navigator : DestinationsNavigator,
    viewModel : HomeViewModel = hiltViewModel()
) {
    val state = viewModel.homeScreenState
    val context = LocalContext.current

    LaunchedEffect(key1 = context){
        viewModel.categoryList.collect { resource ->
            when(resource){
                is Resource.Success -> {
                    //update the ui state to show categories list
                    viewModel.updateScreenState(resource.data)
                }
                is Resource.Error -> {
                    Toast.makeText(
                        context ,
                       "${resource.message}"  ,
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel.updateErrorState(resource.message , resource.data)
                }
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
                Box(modifier = Modifier.fillMaxWidth()) {

                    if (state.categoryList.isEmpty() && state.error != null){
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Could not fetch results" ,
                                fontSize = 14.sp ,
                                color = Color.Black
                            )
                            Button(
                                onClick = {
                                    //refresh
                                 } ,
                                modifier = Modifier
                                    .width(50.dp)
                                    .height(30.dp)
                                    .background(color = Color.Blue),
                            ) {
                                Text(text = "retry" , color = Color.White)
                            }

                        }
                    }

                    if(state.showGridProgressBar){
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center) ,
                            color = Color.Blue,
                            strokeWidth = 3.dp
                        )
                    }
                    if(state.categoryList.isNotEmpty()){
                        LazyVerticalGrid(
                            cells = GridCells.Fixed(2),
                            contentPadding = PaddingValues(8.dp),
                            content = {
                                items(state.categoryList.size){ itemPos ->
                                    CategoryItem(
                                        category = state.categoryList[itemPos],
                                        onCardClicked = { itemId ->
                                              //trigger navigation
                                            Toast.makeText(
                                                context ,
                                                "clicked at category id : $itemId"  ,
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        } ,
                                        context = context ,
                                        state = state
                                    )
                                }
                            }
                        )
                     }
                }

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

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier ,
    category : Categories ,
    onCardClicked: (id : String) -> Unit ,
    context : Context,
    state : HomeScreenStates
){
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
            .clickable {
                onCardClicked(category.id)
            },
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp
    ) {
        Box(
            modifier = Modifier.height(200.dp)
        ) {
   /**         if(state.showCardProgressBar){
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center) ,
                    color = Color.Blue,
                    strokeWidth = 3.dp
                )
            }

   **/
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(context = context)
                        .data(data = category.image)
                        .apply(block = fun ImageRequest.Builder.() {
                            placeholder(R.drawable.ic_launcher_foreground)
                            crossfade(true)
                        }).build()
                ),
                contentDescription = "category diagram",
                modifier = Modifier
                    .fillMaxSize() ,
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ),
                            startY = 50f
                        )
                    ))
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp) ,
                contentAlignment = Alignment.BottomStart
            ){
                Text(
                    text = category.name ,
                    color = Color.White,
                    fontSize = 16.sp ,
                )
            }

        }
    }
}