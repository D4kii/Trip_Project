package br.senai.sp.jandira.trip.repository

import androidx.compose.runtime.Composable
import br.senai.sp.jandira.trip.R
import br.senai.sp.jandira.trip.model.Category
import androidx.compose.ui.res.painterResource

class CategoryRepository {
    companion object{
        @Composable

        fun getCategories(): List<Category> {
            return listOf(
                Category(
                    id = 1,
                    name = "Montain",
                    icon = painterResource(id = R.drawable.montain)
                ),
                Category(
                    id = 1,
                    name = "Beach",
                    icon = painterResource(id = R.drawable.beach)
                ),
                Category(
                    id = 1,
                    name = "Snow",
                    icon = painterResource(id = R.drawable.snow)
                )

            )

        }
    }

}