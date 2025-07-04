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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mahdi.rostamipour.rpstore.intent.FilterIntent
import com.mahdi.rostamipour.rpstore.intent.state.FilterState
import com.mahdi.rostamipour.rpstore.intent.state.ProductState
import com.mahdi.rostamipour.rpstore.viewModel.FilterViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
@Preview(showBackground = true , showSystemUi = true)
fun CategoriesScreen(categoryId : Int = 0,
                     filterViewModel: FilterViewModel = koinViewModel(),navigation : NavHostController = rememberNavController()) {

    val context = LocalContext.current

    val scrollState = rememberScrollState()

    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ){
        Card(Modifier.fillMaxWidth().clickable(true, onClick = {
            navigation.navigate("FilterScreen")
        }),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Sort products")
            }
        }


        val filterByCategory by filterViewModel.getProductByCategoryState.collectAsState()
        LaunchedEffect(true) {
            filterViewModel.handleFilter(FilterIntent.GetProductsByCategory,categoryId)
        }

        when (filterByCategory){
            is FilterState.Idle , is FilterState.Loading->{
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is FilterState.Error -> {
                //Text(text = (filterByCategory as FilterState.Error).message, modifier = Modifier.padding(8.dp), color = Color.Red)
            }

            is FilterState.Success -> {
                val products = (filterByCategory as FilterState.Success).products

                LazyVerticalGrid (
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    contentPadding = PaddingValues(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(products.size) { index ->
                        val product = products[index]

                        Card(
                            modifier = Modifier
                                .width(180.dp)
                                .height(280.dp).clickable(true, onClick = {

                                })
                                .border(2.dp, brush = Brush.linearGradient(
                                    colors = listOf(Color(0xFF73C3F5), Color(0xFF143464))
                                ), RoundedCornerShape(12.dp)),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.Transparent
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                AsyncImage(
                                    model = ImageRequest.Builder(context)
                                        .data(product.picture)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(120.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                )


                                Text(
                                    text = product.title,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Black,
                                    modifier = Modifier
                                        .padding(top = 8.dp , start = 2.dp, end = 2.dp)
                                        .fillMaxWidth()
                                        .wrapContentWidth(Alignment.CenterHorizontally) , fontSize = 12.sp
                                )

                                if (product.offer) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = "${product.price}$",
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                textDecoration = TextDecoration.LineThrough,
                                                color = Color.Gray
                                            )
                                        )
                                        Text(
                                            text = "${product.priceOffer}$",
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                color = Color.Red,
                                                fontWeight = FontWeight.Bold
                                            ), fontSize = 12.sp
                                        )
                                    }
                                } else {
                                    Text(
                                        text = "${product.price}$",
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