package com.dev.james.oauthdemoapp.presentation.screens

import android.content.Context
import android.graphics.drawable.Icon
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.dev.james.oauthdemoapp.R
import com.dev.james.oauthdemoapp.constants.Resource
import com.dev.james.oauthdemoapp.domain.models.Categories
import com.dev.james.oauthdemoapp.domain.models.Part
import com.dev.james.oauthdemoapp.presentation.screens.destinations.CategoryScreenDestination
import com.dev.james.oauthdemoapp.presentation.screens.destinations.FavouritesScreenDestination
import com.dev.james.oauthdemoapp.presentation.screens.states.CategoryScreenState
import com.dev.james.oauthdemoapp.presentation.viewmodel.CategoryScreenViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Destination
@Composable
fun CategoryScreen(
    navigator: DestinationsNavigator ,
    categories: Categories ,
    categoryScreenViewModel: CategoryScreenViewModel = hiltViewModel()
){

     val state = categoryScreenViewModel.categoryScreenState
     val context = LocalContext.current

    LaunchedEffect(key1 = context ){
        categoryScreenViewModel.fetchParts(categories.id).collect{ resource ->
            when(resource){
                is Resource.Success -> {
                    categoryScreenViewModel.updateScreenState(resource.data)
                }
                is Resource.Error -> {
                    categoryScreenViewModel.updateErrorState(resource.message , resource.data)
                }
            }
        }
    }

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState
    ) {

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top ,
        ) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp) , horizontalArrangement = Arrangement.Center) {
                Text(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .fillMaxWidth()
                        .weight(0.75f) ,
                    text = categories.name ,
                    fontSize = 25.sp ,
                    color = Color.Blue
                )
                IconButton(
                    onClick = {
                    //navigate to favourites
                    navigator.navigate(FavouritesScreenDestination)

                    }
                    ) {
                   Icon(imageVector = Icons.Filled.Favorite , contentDescription = "to favourites screen" )
                    }

            }



            SearchBar(onSearchClicked = {
                //trigger search function in vm
                   categoryScreenViewModel.searchFavourite(it)
            }, state = state)


            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {

                if (state.partsList.isEmpty() && state.fetchError.isNullOrEmpty()){
                    Text(
                        text = "No parts available yet for this category" ,
                        fontSize = 14.sp ,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                }

                if(state.partsList.isNotEmpty()){
                    Log.d("CategoryScreen", "show grid with items count: ${state.favouritePartsList.size}")
                    LazyVerticalGrid(cells = GridCells.Fixed(2) , contentPadding = PaddingValues(5.dp)){

                        items(state.partsList.size){ itemPos ->
                            SinglePartCard(part = state.partsList[itemPos], context = context , modifier = Modifier.height(330.dp) ,
                                onCardClicked = { id ->
                                    //navigate to part details screen
                                } ,
                                onIsFavouriteClicked = { partId ->
                                    categoryScreenViewModel.toggleFavourite(partId)
                                    scope.launch {
                                        if(!state.partsList[itemPos].isFavourite){
                                            scaffoldState.snackbarHostState.showSnackbar(
                                                "${state.partsList[itemPos].name} added to my favourites"
                                            )
                                        }else{
                                            scaffoldState.snackbarHostState.showSnackbar(
                                                "${state.partsList[itemPos].name} removed from my favourites"
                                            )
                                        }
                                    }

                                }
                            )

                        }
                    }
                }
            }

            if(state.showProgressBar){
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp),
                    color = Color.Blue,
                    strokeWidth = 2.dp
                )
            }
            if (state.partsList.isEmpty() && state.fetchError != null){
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = state.fetchError ,
                        fontSize = 14.sp ,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                    Button(
                        onClick = {
                            //refresh
                        } ,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(text = "retry" , color = Color.White)
                    }

                }
            }

        }

    }

}


@Composable
fun SinglePartCard(
    modifier: Modifier = Modifier,
    part : Part,
    context: Context ,
    onCardClicked : (id :String) -> Unit,
    onIsFavouriteClicked : (id : String) -> Unit
){

    Log.d("CategoryScreen", "SinglePartCard: data received: $part")
    Card(
        modifier = modifier
            .padding(8.dp)
            .clickable {
                //navigate to parts details screen with part id
                onCardClicked(part.id)
            },
        shape = RoundedCornerShape(15.dp),
        elevation = 2.dp
    ) {
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.40f),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(context = context )
                            .data(data = part.image)
                            .apply(block = fun ImageRequest.Builder.() {
                                placeholder(R.drawable.ic_launcher_foreground)
                                crossfade(true)
                            }).build()
                    ),
                    contentDescription = "part diagram",
                    modifier = Modifier
                        .fillMaxSize() ,
                    contentScale = ContentScale.FillBounds
                )

                Box( modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp) ,
                    contentAlignment = Alignment.TopEnd
                ) {
                    val icon = if(part.isFavourite)
                        Icons.Filled.Favorite
                    else
                        Icons.Filled.FavoriteBorder

                    FavouriteIcon(part = part  , icon = icon  ){ partId ->
                        //trigger like process in screen
                        onIsFavouriteClicked(partId)
                    }
                }

            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.5f)
                    .padding(start = 10.dp, end = 10.dp, top = 4.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top

            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = part.name,
                    color = Color.Black ,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = FontFamily.SansSerif ,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold

                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "OEM:${part.oemNumber}",
                    color = Color.Black ,
                    fontFamily = FontFamily.SansSerif ,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Material : ${part.material}",
                    color = Color.Black ,
                    fontFamily = FontFamily.SansSerif ,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    text = "Ksh.${part.price}",
                    color = Color.Black ,
                    fontFamily = FontFamily.SansSerif ,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Ksh.${part.dealerPrice}",
                    color = Color.DarkGray ,
                    fontFamily = FontFamily.SansSerif ,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    textDecoration = TextDecoration.LineThrough
                )

                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    shape = RoundedCornerShape(5.dp)
                ){
                    Text(
                        text = "ADD TO CART",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 14.sp,
                        fontFamily = FontFamily.SansSerif
                    )
                }


            }

        }

    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier ,
    onSearchClicked : (query : String) -> Unit ,
    state : CategoryScreenState
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        var text by rememberSaveable() {
            mutableStateOf("")
        }
        OutlinedTextField(
            value = text ,
            onValueChange = {
                //trigger search action in viewmodel
                   text = it
            },
            placeholder = {
                Text(
                    text = "Search",
                    //color = primaryGray
                )
            },

            modifier = Modifier
                .fillMaxWidth()
                .weight(0.75f)
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .padding(start = 8.dp, end = 8.dp),
            shape = RoundedCornerShape(size = 8.dp),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                autoCorrect = true,
                keyboardType = KeyboardType.Text,
            ),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                disabledTextColor = Color.Gray,
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Blue,
                unfocusedIndicatorColor = Color.Gray,
                disabledIndicatorColor = Color.Gray
            ),
            textStyle = TextStyle(color = Color.Black),
            maxLines = 1,
            singleLine = true,
            leadingIcon = {
                Icon(
                    modifier = Modifier
                        .size(24.dp),
                    imageVector = Icons.Filled.Search,
                    contentDescription = null,
                    tint = Gray
                )
            }
        )

        Button(
            onClick = {
                onSearchClicked(text)
            } ,
            modifier = modifier
                .padding(4.dp)
                .height(50.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "search")
        }

    }
}
@Composable
fun FavouritePartsRow(
    modifier: Modifier = Modifier,
    partsList : List<Part> ,
    context: Context,
    onCardClicked : (id :String) -> Unit,
    onIsFavouriteClicked : (id : String) -> Unit
){
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
      items(partsList.size){ partIndex ->
          SinglePartCard(
              part = partsList[partIndex],
              context = context,
              onCardClicked = { id ->
                   onCardClicked(id)
              },
              onIsFavouriteClicked = {
                  onIsFavouriteClicked(it)
              },
              modifier = Modifier.height(330.dp)
          )

      }
    }
}

@Composable
fun FavouriteIcon(
part : Part,
icon : ImageVector,
modifier : Modifier = Modifier ,
onLikeClicked : (partId : String) -> Unit
){
    Icon(
        imageVector = icon,
        contentDescription = "favourite icon",
        modifier = modifier.clickable {
            //invoke like function
            onLikeClicked(part.id)
        }
    )


}