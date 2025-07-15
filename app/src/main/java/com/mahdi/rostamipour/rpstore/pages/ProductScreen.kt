package com.mahdi.rostamipour.rpstore.pages

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mahdi.rostamipour.rpstore.intent.CommentsIntent
import com.mahdi.rostamipour.rpstore.intent.state.GetCommentsState
import com.mahdi.rostamipour.rpstore.model.ProductsModel
import com.mahdi.rostamipour.rpstore.viewModel.CommentsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProductScreen(model : ProductsModel? , commentsViewModel: CommentsViewModel = koinViewModel()) {

    val context = LocalContext.current
    val scrollState = rememberScrollState()


    Scaffold(
        bottomBar = {
            Button(
                onClick = { Toast.makeText(context,"Added to cart" , Toast.LENGTH_SHORT).show() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp , bottom = 8.dp),
                shape = MaterialTheme.shapes.small
            ) {
                Text("Add to cart")
            }
        }
    ) { paddingValues ->


        val getCommentsState by commentsViewModel.getCommentsState.collectAsState()
        LaunchedEffect(true) {
            commentsViewModel.handleComment(CommentsIntent.GetCommentsProduct, model?.id ?: 0)
        }

        when(getCommentsState){
            is GetCommentsState.Idle , is GetCommentsState.Loading ->{

            }

            is GetCommentsState.Error -> {

            }

            is GetCommentsState.Success -> {

                val comments = (getCommentsState as GetCommentsState.Success).comments



                LazyColumn(modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues) ,
                    contentPadding = PaddingValues(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)) {

                    item{
                        AsyncImage(
                            model = ImageRequest.Builder(context)
                                .data(model?.picture)
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            modifier = Modifier.height(400.dp).fillMaxWidth()

                        )

                        Text(
                            model?.title ?: "",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                            fontSize = 16.sp,
                            modifier = Modifier.padding(4.dp)
                        )

                        ExpandableText(
                            model?.description ?: ""
                        )

                        Row(Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.Absolute.SpaceBetween) {
                            Text(
                                "Comments",
                                fontSize = 12.sp,
                                modifier = Modifier.wrapContentWidth(Alignment.Start)
                            )

                            Text(
                                "Add comment",
                                fontSize = 12.sp,
                                modifier = Modifier.wrapContentWidth(Alignment.End),
                                color = Color.Blue
                            )
                        }

                    }


                    items(comments.size){
                        Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {

                            Row(Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.Absolute.SpaceBetween) {
                                Text(
                                    comments[it].date,
                                    fontSize = 12.sp,
                                    modifier = Modifier.wrapContentWidth(Alignment.Start)
                                )

                                Text(
                                    comments[it].userName,
                                    fontSize = 12.sp,
                                    modifier = Modifier.wrapContentWidth(Alignment.End),
                                    color = Color.Blue
                                )
                            }

                            Text(
                                comments[it].comment,
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                fontSize = 16.sp,
                                modifier = Modifier.padding(top = 4.dp)
                            )

                        }
                    }

                }
            }
        }
    }

}

@Composable
fun ExpandableText(
    text: String,
    minimizedMaxLines: Int = 3
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(horizontal = 8.dp)) {
        Text(
            text = text,
            fontSize = 12.sp,
            maxLines = if (isExpanded) Int.MAX_VALUE else minimizedMaxLines,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.animateContentSize()
        )

        if (text.length > 100) { // فقط اگر متن طولانیه دکمه نمایش بده
            Text(
                text = if (isExpanded) "See less" else "See more",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .clickable { isExpanded = !isExpanded }
            )
        }
    }
}