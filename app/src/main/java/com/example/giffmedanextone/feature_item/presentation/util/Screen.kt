package com.example.giffmedanextone.feature_item.presentation.util

sealed class Screen(val route: String) {
    object ListsScreen : Screen ("lists_screen")
    object AddEditSingleListScreen : Screen("single_list_screen")
}
