package com.dev.james.oauthdemoapp.presentation.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dev.james.oauthdemoapp.presentation.screens.destinations.CategoryScreenDestination
import com.dev.james.oauthdemoapp.presentation.viewmodel.FavouritesViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Destination
@Composable
fun FavouritesScreen(
    navigator: DestinationsNavigator,
    viewModel : FavouritesViewModel = hiltViewModel()
){
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val state = viewModel.favScreenState
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState
    ) {

        Column(
            verticalArrangement = Arrangement.Center ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopToolBar(){
                navigator.popBackStack()
            }

            if(state.favPartsList.isEmpty()){
                Text(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .fillMaxWidth() ,
                    text = "No favourites added yet. Please browse around and select your favourites" ,
                    fontSize = 14.sp ,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }

            if(state.favPartsList.isNotEmpty()){
                Log.d("CategoryScreen", "show grid with items count: ${state.favPartsList.size}")
                LazyVerticalGrid(cells = GridCells.Fixed(2) , contentPadding = PaddingValues(5.dp)){

                    items(state.favPartsList.size){ itemPos ->
                        SinglePartCard(part = state.favPartsList[itemPos], context = context , modifier = Modifier.height(330.dp) ,
                            onCardClicked = { id ->
                                //navigate to part details screen
                            } ,
                            onIsFavouriteClicked = { partId ->

                                scope.launch {
                                    if(!state.favPartsList[itemPos].isFavourite){
                                        scaffoldState.snackbarHostState.showSnackbar(
                                            "${state.favPartsList[itemPos].name} added to my favourites"
                                        )
                                    }else{
                                        scaffoldState.snackbarHostState.showSnackbar(
                                            "${state.favPartsList[itemPos].name} removed from my favourites"
                                        )
                                    }

                                }

                            }
                        )

                    }
                }
            }

        }


    }



}

@Composable
fun TopToolBar(
    modifier: Modifier = Modifier,
    onBackPressed : () -> Unit
){
    Row(modifier = modifier
        .fillMaxWidth()
        .padding(top = 8.dp, start = 8.dp, bottom = 16.dp , end = 8.dp)) {
        Text(
            modifier = modifier
                .fillMaxWidth()
                .weight(0.75f) ,
            text = "Favourites" ,
            fontSize = 25.sp ,
            color = Color.Blue
        )
        TextButton(
            onClick = { onBackPressed() },
            modifier = modifier.height(50.dp).padding(bottom = 8.dp)
        ) {
            Text(text = "back" , fontSize = 16.sp , color = Color.Blue)
        }
    }

}
