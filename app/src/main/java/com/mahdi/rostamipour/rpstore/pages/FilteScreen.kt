package com.mahdi.rostamipour.rpstore.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mahdi.rostamipour.rpstore.intent.FilterIntent
import com.mahdi.rostamipour.rpstore.intent.state.FilteringItemsState
import com.mahdi.rostamipour.rpstore.viewModel.FilterViewModel
import org.koin.androidx.compose.koinViewModel
import kotlin.div
import kotlin.text.toInt

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun FilterScreen(filterViewModel: FilterViewModel = koinViewModel(),navigation : NavHostController = rememberNavController()){

    val getFilterItemsState by filterViewModel.getFilteringItemsState.collectAsState()

    LaunchedEffect(true) {
        filterViewModel.handleFilter(FilterIntent.GetFilteringItems,0)
    }

    when (getFilterItemsState) {

        is FilteringItemsState.Idle , is FilteringItemsState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is FilteringItemsState.Success -> {

            val brands = (getFilterItemsState as FilteringItemsState.Success).filterItems.Brand
            val colors = (getFilterItemsState as FilteringItemsState.Success).filterItems.Color
            val price = (getFilterItemsState as FilteringItemsState.Success).filterItems.Price

            val maxPriceValue = price.maxPrice.toFloat()
            val minPriceValue = price.minPrice.toFloat()

            val correctedMaxPriceValue = maxOf(maxPriceValue, minPriceValue)
            val correctedMinPriceValue = minOf(maxPriceValue, minPriceValue)
            val steps = ((correctedMaxPriceValue - correctedMinPriceValue) / 100).toInt()


            Column (Modifier.fillMaxSize().padding(8.dp)) {


                Text("Brand" , modifier = Modifier.padding(8.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    Modifier.fillMaxWidth()
                ) {
                    items(brands.size) {
                        var isChecked by remember { mutableStateOf(false) }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                        ) {
                            Checkbox(
                                checked = isChecked,
                                onCheckedChange = { checked ->
                                    isChecked = checked
                                }
                            )
                            Text(text = brands[it].title)
                        }
                    }
                }

                Text("Colors" , modifier = Modifier.padding(8.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    Modifier.fillMaxWidth()
                ) {
                    items(colors.size) {
                        var isChecked by remember { mutableStateOf(false) }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                        ) {
                            Checkbox(
                                checked = isChecked,
                                onCheckedChange = { checked ->
                                    isChecked = checked
                                }
                            )
                            Text(text = colors[it].title)
                        }
                    }
                }

                var sliderPosition by remember { mutableStateOf(0f..maxPriceValue) }

                Text(
                    text = "Price range",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .padding(8.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${sliderPosition.start.toInt().toString()}$",
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${sliderPosition.endInclusive.toInt().toString()}$",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))


             /*   RangeSlider(
                    value = sliderPosition,
                    onValueChange = {
                        sliderPosition = it
                    },
                    valueRange = correctedMinPriceValue..correctedMaxPriceValue,
                    steps = steps,
                    colors = SliderDefaults.colors(
                        thumbColor = Color(0xFF00BCD4),
                        activeTrackColor = Color(0xFF00BCD4)
                    )
                )*/
                RangeSlider(
                    value = sliderPosition,
                    onValueChange = {
                        sliderPosition = it
                    },
                    valueRange = correctedMinPriceValue..correctedMaxPriceValue,
                    steps = steps,
                    colors = SliderDefaults.colors(
                        activeTrackColor = Color(0xFF00BCD4),  // آبی فیروزه‌ای
                        inactiveTrackColor = Color.LightGray,
                        thumbColor = Color(0xFF00BCD4),
                        activeTickColor = Color.Transparent,
                        inactiveTickColor = Color.Transparent
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Cheapest", textAlign = TextAlign.Center , fontSize = 12.sp)
                    Text("Most Expensive", textAlign = TextAlign.Center, fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { navigation.popBackStack() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Show Products")
                }


            }

        }

        is FilteringItemsState.Error -> {
            Text(text = (getFilterItemsState as FilteringItemsState.Error).message, modifier = Modifier.padding(8.dp))
        }
    }


}