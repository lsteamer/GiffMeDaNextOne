package com.example.giffmedanextone.feature_item.presentation

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Colors
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.giffmedanextone.feature_item.domain.model.SingleList
import com.example.giffmedanextone.feature_item.presentation.lists.components.SingleListItem
import com.example.giffmedanextone.ui.theme.GiffMeDaNextOneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Greeting(name = "Steamer")
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

val list = SingleList(
    "Movie",
    "Matrix",
    emptyList(),
    emptyList(),
    Color.DKGRAY,
    100,
    100
)

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GiffMeDaNextOneTheme {

        SingleListItem(list = list)

    }
}