package br.senai.sp.jandira.trip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.senai.sp.jandira.trip.model.Category
import br.senai.sp.jandira.trip.repository.CategoryRepository
import br.senai.sp.jandira.trip.ui.theme.TripTheme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TripTheme {
                HomeScreen(CategoryRepository.getCategories())

            }
        }
    }
}

@Composable
fun HomeScreen(categories: List<Category>) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp), backgroundColor = Color.Magenta
            ) {
                Image(
                    painter = painterResource(id = R.drawable.paris),
                    contentDescription = "Logo",
                    contentScale = ContentScale.Crop
                )

            }
            Text(
                text = stringResource(id = R.string.categories),
                modifier = Modifier.padding(top = 14.dp, start = 16.dp)
            )
            LazyRow() {
                items(categories) {
                    Card(
                        modifier = Modifier
                            .size(
                                110.dp,
                                130.dp
                            )
                            .padding(4.dp),
                        backgroundColor = Color(207, 6, 240)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                painter = it.icon!!,
                                contentDescription = "${it.name}"
                            )
                            Text(text = it.name)
                        }

                    }
                }

            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview3() {
    TripTheme {
        HomeScreen(CategoryRepository.getCategories())
    }
}