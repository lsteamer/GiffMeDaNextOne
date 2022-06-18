package com.anjegonz.giffmedanextone.feature_item.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.anjegonz.giffmedanextone.feature_item.presentation.add_edit_single_list.AddEditSingleListScreen
import com.anjegonz.giffmedanextone.feature_item.presentation.lists.ListsScreen
import com.anjegonz.giffmedanextone.feature_item.presentation.lists.components.SingleListItem
import com.anjegonz.giffmedanextone.feature_item.presentation.util.Screen
import com.anjegonz.giffmedanextone.ui.theme.GiffMeDaNextOneTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GiffMeDaNextOneTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.ListsScreen.route
                    ) {
                        composable(route = Screen.ListsScreen.route) {
                            ListsScreen(navController = navController)
                        }
                        composable(route = Screen.AddEditSingleListScreen.route +
                                "?listId={listId}&listColor={listColor}",
                            arguments = listOf(
                                navArgument(
                                    name = "listId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name = "listColor"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            val color = it.arguments?.getInt("listColor") ?: -1
                            AddEditSingleListScreen(
                                navController = navController,
                                listColor = color
                            )
                        }
                    }
                }


            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GiffMeDaNextOneTheme {
        SingleListItem()
    }
}