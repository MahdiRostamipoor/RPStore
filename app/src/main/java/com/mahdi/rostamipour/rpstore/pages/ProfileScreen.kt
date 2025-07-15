package com.mahdi.rostamipour.rpstore.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mahdi.rostamipour.rpstore.R
import com.mahdi.rostamipour.rpstore.intent.ProfileIntent
import com.mahdi.rostamipour.rpstore.intent.state.ProfileState
import com.mahdi.rostamipour.rpstore.viewModel.ProfileViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
@Preview(showSystemUi = true , showBackground = true)
fun ProfileScreen(navigation : NavHostController = rememberNavController() ,
                  profileViewModel: ProfileViewModel = koinViewModel()){

    val scrollVertical = rememberScrollState()

    val getProfileState by profileViewModel.getProfileState.collectAsState()

    LaunchedEffect(true) {
        profileViewModel.handleProfileIntent(ProfileIntent.GetProfile)
    }

    when(getProfileState){
        is ProfileState.Idle , is ProfileState.Loading-> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                CircularProgressIndicator()
            }
        }

        is ProfileState.Error -> {
            Text(text = (getProfileState as ProfileState.Error).message, modifier = Modifier.padding(8.dp))
        }

        is ProfileState.Success -> {

            val profile = (getProfileState as ProfileState.Success).profile

            Column (
                Modifier.fillMaxSize()
                    .verticalScroll(scrollVertical).padding(top = 16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {

                Text(
                    "Your Profile",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    fontSize = 24.sp, modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                        .wrapContentWidth(Alignment.CenterHorizontally) , color = Color.Blue)

                Spacer(modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp).height(0.5.dp).background(Color.Gray))

                Text(
                    profile.name,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    fontSize = 20.sp, modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                )

                Text(profile.email, fontSize = 12.sp, modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp))

                Card(modifier = Modifier.fillMaxWidth().padding(start = 6.dp, end = 6.dp , top = 10.dp, bottom = 14.dp),
                    shape = RoundedCornerShape(8.dp), colors = CardDefaults.cardColors(Color.Transparent)
                    ,elevation = CardDefaults.cardElevation(1.dp)) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp, top = 12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                tint = Color.Blue,
                                contentDescription = "Favorite",
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                "Your Pro subscription date",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                fontSize = 16.sp ,
                            )
                        }


                        Text(profile.membershipDate, fontSize = 12.sp,
                            modifier = Modifier.padding( start = 8.dp, end = 8.dp, bottom = 12.dp, top = 4.dp))
                    }

                }

                Row(modifier = Modifier.fillMaxWidth().padding( 12.dp), horizontalArrangement = Arrangement.SpaceBetween) {

                    Row {
                        Icon(
                            painter = painterResource(id = R.drawable.wallet),
                            contentDescription = "",
                            modifier = Modifier.size(20.dp)
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Text(
                            "Transactions and wallet",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                            fontSize = 14.sp , modifier = Modifier.wrapContentWidth(Alignment.Start),
                        )

                    }

                    Text(">", modifier = Modifier.wrapContentWidth(Alignment.End))

                }
                Spacer(Modifier.fillMaxWidth().padding(bottom = 8.dp).height(0.5.dp).background(Color.Gray))

                Row(modifier = Modifier.fillMaxWidth().padding( 12.dp), horizontalArrangement = Arrangement.SpaceBetween) {

                    Row {
                        Icon(
                            painter = painterResource(id = R.drawable.points),
                            contentDescription = "",
                            modifier = Modifier.size(20.dp)
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Column(modifier = Modifier.wrapContentWidth(Alignment.Start)) {
                            Text(
                                "Your points",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                fontSize = 14.sp)

                            Text("${profile.points} points" , color = Color.Blue, fontSize = 12.sp)
                        }



                    }


                    Text(">", modifier = Modifier.wrapContentWidth(Alignment.End))

                }
                Spacer(Modifier.fillMaxWidth().padding(bottom = 8.dp).height(0.5.dp).background(Color.Gray))


                Row(modifier = Modifier.fillMaxWidth().padding( 12.dp), horizontalArrangement = Arrangement.SpaceBetween) {

                    Row {
                        Icon(
                            painter = painterResource(id = R.drawable.bank),
                            contentDescription = "",
                            modifier = Modifier.size(20.dp)
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Text(
                            "Bank cards",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                            fontSize = 14.sp , modifier = Modifier.wrapContentWidth(Alignment.Start),
                        )

                    }

                    Text(">", modifier = Modifier.wrapContentWidth(Alignment.End))

                }
                Spacer(Modifier.fillMaxWidth().padding(bottom = 8.dp).height(0.5.dp).background(Color.Gray))

                Row(modifier = Modifier.fillMaxWidth().padding( 12.dp), horizontalArrangement = Arrangement.SpaceBetween) {

                    Row {
                        Icon(
                            painter = painterResource(id = R.drawable.discount),
                            contentDescription = "",
                            modifier = Modifier.size(20.dp)
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Text(
                            "Discounts",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                            fontSize = 14.sp , modifier = Modifier.wrapContentWidth(Alignment.Start),
                        )

                    }

                    Text(">", modifier = Modifier.wrapContentWidth(Alignment.End))

                }

                Spacer(Modifier.fillMaxWidth().padding(bottom = 8.dp).height(0.5.dp).background(Color.Gray))

                Row(modifier = Modifier.fillMaxWidth().clickable(true, onClick = {
                    navigation.navigate("FavoriteScreen")
                }).padding( 12.dp), horizontalArrangement = Arrangement.SpaceBetween) {

                    Row {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "",
                            modifier = Modifier.size(20.dp)
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Text(
                            "My Favorites",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                            fontSize = 14.sp , modifier = Modifier.wrapContentWidth(Alignment.Start),
                        )

                    }

                    Text(">", modifier = Modifier.wrapContentWidth(Alignment.End))

                }

                Spacer(Modifier.fillMaxWidth().padding(bottom = 8.dp).height(0.5.dp).background(Color.Gray))


                Row(modifier = Modifier.fillMaxWidth().padding( 12.dp), horizontalArrangement = Arrangement.SpaceBetween) {

                    Row {
                        Icon(
                            painter = painterResource(id = R.drawable.comment),
                            contentDescription = "",
                            modifier = Modifier.size(20.dp)
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Text(
                            "My Comments",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                            fontSize = 14.sp , modifier = Modifier.wrapContentWidth(Alignment.Start),
                        )

                    }

                    Text(">", modifier = Modifier.wrapContentWidth(Alignment.End))

                }


                Spacer(Modifier.fillMaxWidth().padding(bottom = 8.dp).height(12.dp).background(Color.Gray))

                Row(modifier = Modifier.fillMaxWidth().padding( 12.dp), horizontalArrangement = Arrangement.SpaceBetween) {

                    Row {
                        Icon(
                            painter = painterResource(R.drawable.support),
                            contentDescription = "",
                            modifier = Modifier.size(20.dp)
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Text(
                            "Support",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                            fontSize = 14.sp , modifier = Modifier.wrapContentWidth(Alignment.Start),
                        )

                    }

                    Text(">", modifier = Modifier.wrapContentWidth(Alignment.End))

                }

                Spacer(Modifier.fillMaxWidth().padding(bottom = 8.dp).height(0.5.dp).background(Color.Gray))

                Row(modifier = Modifier.fillMaxWidth().padding( 12.dp), horizontalArrangement = Arrangement.SpaceBetween) {

                    Row {
                        Icon(
                            painter = painterResource(R.drawable.logout),
                            contentDescription = "",
                            modifier = Modifier.size(20.dp)
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Text(
                            "Logout",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                            fontSize = 14.sp , modifier = Modifier.wrapContentWidth(Alignment.Start),
                        )

                    }

                    Text(">", modifier = Modifier.wrapContentWidth(Alignment.End))

                }

            }
        }
    }



}