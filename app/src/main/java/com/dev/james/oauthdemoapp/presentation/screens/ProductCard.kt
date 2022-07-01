package com.dev.james.oauthdemoapp.presentation.screens

import android.graphics.Color
import android.widget.ImageButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dev.james.oauthdemoapp.R

@Composable
@Preview()
fun SinglePartCard(){
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally ,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                        .clip(shape = RoundedCornerShape(15.dp)),
                    painter = painterResource(id = R.drawable.ic_baseline_image_24),
                    contentDescription = "test image",

                )

                Box( modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp) ,
                    contentAlignment = Alignment.TopEnd
                ) {
                    Image(
                        modifier = Modifier.height(40.dp).width(40.dp),
                        painter = painterResource(id = R.drawable.ic_favorite_border),
                        contentDescription = "test image"
                    )
                }

            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.5f)
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top

            ) {
                Text(
                    text = "HETACHI p1000d7 engine spark plug",
                    color = Black ,
                    fontFamily = FontFamily.SansSerif ,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold

                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "OEM : MX02",
                    color = Black ,
                    fontFamily = FontFamily.SansSerif ,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Light
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Material : Rubber",
                    color = Black ,
                    fontFamily = FontFamily.SansSerif ,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Light
                )

                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Ksh.4000",
                    color = Black ,
                    fontFamily = FontFamily.SansSerif ,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(36.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth() ,
                    verticalAlignment = Alignment.CenterVertically ,
                    horizontalArrangement = Arrangement.Start
                ) {
                    IconButton(
                        onClick = { /*TODO*/ } ,
                        modifier = Modifier
                            .background(color = Blue)
                            .clip(shape = CircleShape)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_remove_24)
                            , contentDescription = "decrease count" ,
                            tint = White
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = "0",
                        fontWeight = FontWeight.Bold ,
                        fontSize = 30.sp
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    IconButton(
                        onClick = { /*TODO*/ } ,
                        modifier = Modifier
                            .background(color = Blue)
                            .clip(shape = CircleShape)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_add_24)
                            , contentDescription = "increase count" ,
                            tint = White
                        )
                    }

                }
                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(Blue),
                    shape = RoundedCornerShape(10.dp)
                ){
                    Text(
                        text = "ADD TO CART",
                        fontWeight = FontWeight.Bold,
                        color = White,
                        fontFamily = FontFamily.SansSerif
                    )
                }


            }

        }

    }
}