package com.mahdi.rostamipour.rpstore.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
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
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mahdi.rostamipour.rpstore.intent.CategoryIntent
import com.mahdi.rostamipour.rpstore.intent.FilterIntent
import com.mahdi.rostamipour.rpstore.intent.ProductIntent
import com.mahdi.rostamipour.rpstore.intent.state.CategoryState
import com.mahdi.rostamipour.rpstore.intent.state.FilterState
import com.mahdi.rostamipour.rpstore.intent.state.ProductState
import com.mahdi.rostamipour.rpstore.viewModel.CategoryViewModel
import com.mahdi.rostamipour.rpstore.viewModel.FilterViewModel
import com.mahdi.rostamipour.rpstore.viewModel.ProductViewModel
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun HomeScreen(
    categoryViewModel: CategoryViewModel = koinViewModel() , productViewModel: ProductViewModel = koinViewModel() ,
    filterViewModel: FilterViewModel = koinViewModel(),navigation : NavHostController = rememberNavController()
) {

    val scrollState = rememberScrollState()

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Card(
            Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            )
        ) {
            var searchText by remember { mutableStateOf("") }

            OutlinedTextField(
                value = searchText,
                onValueChange = { it: String ->
                    searchText = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                placeholder = { Text("Search..." , color = Color.Black) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Home Icon"
                    )
                },
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )
        }


        val context = LocalContext.current
        val imageUrl = "http://10.0.85.2:3000/pics/banner.png"

        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .padding(8.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.FillBounds,
        )

        val categoryState by categoryViewModel.categoryState.collectAsState()

        LaunchedEffect(true) {
            categoryViewModel.handleIntent(CategoryIntent.LoadCategories)
        }

        when (categoryState){
            is CategoryState.Error -> {
                //Text(text = (categoryState as CategoryState.Error).message, modifier = Modifier.padding(8.dp), color = Color.Red)
                //Timber.e((categoryState as CategoryState.Error).message , "get categories error")
            }

            is CategoryState.Idle, is CategoryState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is CategoryState.Success -> {
                val categories = (categoryState as CategoryState.Success).categories
                LazyRow(Modifier.fillMaxWidth().padding(top = 8.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                    items(categories.size){
                        Card(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.Transparent
                            ),
                            shape = RoundedCornerShape(12.dp)) {

                            Column(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp).clickable(true, onClick = {
                                        navigation.navigate("CategoriesScreen/${categories[it].id}")

                                    }),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {

                                Box(
                                    modifier = Modifier
                                        .size(100.dp)
                                        .background(
                                            brush = Brush.linearGradient(
                                                colors = listOf(Color(0xFF73C3F5), Color(0xFF143464)) // طیف آبی
                                            ),
                                            shape = CircleShape
                                        )
                                        .clip(CircleShape)
                                ) {
                                    AsyncImage(
                                        model = ImageRequest.Builder(context)
                                            .data(categories[it].picture)
                                            .crossfade(true)
                                            .build(),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(CircleShape)
                                    )
                                }

                                Text(
                                    text = categories[it].title,
                                    modifier = Modifier.padding(top = 8.dp),
                                    color = Color.Black, fontSize = 12.sp
                                )
                            }

                        }
                    }

                }
            }
        }

        val allProductState by productViewModel.allProductState.collectAsState()

        LaunchedEffect(true) {
            productViewModel.handleAllProductIntent(ProductIntent.GetProducts)
        }

        when(allProductState){

            is ProductState.Error -> {
                //Text(text = (allProductState as ProductState.Error).message, modifier = Modifier.padding(8.dp), color = Color.Red)

            }

            is ProductState.Idle, is ProductState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is ProductState.Success -> {

                val products = (allProductState as ProductState.Success).allProduct

                Row(Modifier.fillMaxWidth().padding(top = 8.dp),horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Last Products", modifier = Modifier.padding(12.dp).wrapContentWidth(Alignment.Start)
                        , color = Color.Black , fontSize = 15.sp)

                    Text("Show all->", modifier = Modifier.padding(8.dp).wrapContentWidth(Alignment.End).
                    clickable(true, onClick = {

                    }), color = Color.Blue
                        , fontSize = 15.sp)

                }


                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
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

        val filterByCategory by filterViewModel.getProductByCategoryState.collectAsState()
        LaunchedEffect(true) {
            filterViewModel.handleFilter(FilterIntent.GetProductsByCategory,4)
        }

        when (filterByCategory){
            is FilterState.Idle , is FilterState.Loading->{
                //
            }

            is FilterState.Error -> {
                //Text(text = (allProductState as ProductState.Error).message, modifier = Modifier.padding(8.dp), color = Color.Red)
            }

            is FilterState.Success -> {
                val products = (filterByCategory as FilterState.Success).products

                Text("Favorite Products", modifier = Modifier.padding(12.dp).wrapContentWidth(Alignment.Start)
                    , color = Color.Black , fontSize = 15.sp)


                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
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