package com.mahdi.rostamipour.rpstore.pages

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mahdi.rostamipour.rpstore.R
import com.mahdi.rostamipour.rpstore.intent.FavoriteIntent
import com.mahdi.rostamipour.rpstore.model.FavoriteModel
import com.mahdi.rostamipour.rpstore.model.ProductsModel
import com.mahdi.rostamipour.rpstore.viewModel.FavoriteViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoriteScreen(navigation : NavHostController , favoriteViewModel: FavoriteViewModel = koinViewModel()) {

    val favoriteProductsState by favoriteViewModel.favoriteProductsState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(true) {
        favoriteViewModel.handleIntent(FavoriteIntent.LoadFavoriteProducts)
    }

    val products = favoriteProductsState.success

    if (products.isEmpty()){
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center ,
            horizontalAlignment = Alignment.CenterHorizontally) {

            Icon(painter = painterResource(R.drawable.ic_empty) , contentDescription = "" , tint = Color.Unspecified
                ,modifier = Modifier.size(100.dp))

            Text(text = "Your favorite list is empty" ,  fontSize = 18.sp, modifier = Modifier.padding(top = 12.dp))

        }
    }else {
        LazyColumn(modifier = Modifier.padding(top = 8.dp).fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(products.size) { index->

                Box(modifier = Modifier.fillMaxSize().padding(8.dp)){
                    Card(modifier = Modifier
                        .fillMaxWidth().clickable(true, onClick = {

                            val product = ProductsModel(products[index].category ,products[index].description,
                                products[index].id,products[index].offer,products[index].picture,
                                products[index].price,products[index].priceOffer,products[index].title)

                            navigation.currentBackStackEntry?.savedStateHandle?.set("product", product)
                            navigation.navigate("ProductScreen")
                        })
                        .border(2.dp, brush = Brush.linearGradient(
                            colors = listOf(Color(0xFF73C3F5), Color(0xFF143464))
                        ), RoundedCornerShape(12.dp)),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Transparent
                        )) {

                        Row(modifier = Modifier.fillMaxWidth().padding( 12.dp), horizontalArrangement = Arrangement.SpaceBetween) {

                            Row {
                                AsyncImage(
                                    model = ImageRequest.Builder(context)
                                        .data(products[index].picture)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(80.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                )

                                Spacer(modifier = Modifier.width(6.dp))

                                Column(modifier = Modifier.wrapContentWidth(Alignment.Start)) {
                                    Text(
                                        products[index].title,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                        fontSize = 14.sp)

                                    if (products[index].offer) {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(
                                                text = "${products[index].price}$",
                                                style = MaterialTheme.typography.bodySmall.copy(
                                                    textDecoration = TextDecoration.LineThrough,
                                                    color = Color.Gray
                                                )
                                            )
                                            Text(
                                                text = "${products[index].priceOffer}$",
                                                style = MaterialTheme.typography.bodyMedium.copy(
                                                    color = Color.Red,
                                                    fontWeight = FontWeight.Bold
                                                ), fontSize = 12.sp
                                            )
                                        }
                                    } else {
                                        Text(
                                            text = "${products[index].price}$",
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                color = Color.Black,
                                                fontWeight = FontWeight.SemiBold
                                            ),
                                            modifier = Modifier.padding(top = 4.dp), fontSize = 12.sp
                                        )
                                    }
                                }

                            }

                        }

                    }
                }
            }
        }
    }

}