package com.anjegonz.giffmedanextone.feature_item.presentation.lists

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.anjegonz.giffmedanextone.R
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.anjegonz.giffmedanextone.feature_item.presentation.lists.components.OrderSection
import com.anjegonz.giffmedanextone.feature_item.presentation.lists.components.SingleListItem
import com.anjegonz.giffmedanextone.feature_item.presentation.util.Screen
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun ListsScreen(
    navController: NavController,
    viewModel: ListsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()

    val errorMessage: String = stringResource(id = R.string.next_one_error_message_label)

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                ListsViewModel.UIEvent.ShowErrorSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = errorMessage
                    )
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .padding(top = 25.dp, bottom = 45.dp),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.AddEditSingleListScreen.route)
            }, backgroundColor = MaterialTheme.colors.primary) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.button_add_description)
                )
            }
        }, scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.h4
                )
                IconButton(
                    onClick = {
                        viewModel.onEvent(ListsEvent.ToggleOrderSection)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Sort,
                        contentDescription = stringResource(id = R.string.button_sort)
                    )
                }

            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    listOrder = state.listsOrder,
                    onOrderChange = {
                        viewModel.onEvent(ListsEvent.Order(it))
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()) {


                items(
                    items = state.lists,
                    key = { list ->
                        list.hashCode()
                    }
                ) { list ->

                    val dismissState = rememberDismissState(
                        confirmStateChange = {
                            if (it == DismissValue.DismissedToStart) {
                                viewModel.onEvent(
                                    ListsEvent.DeleteList(list)
                                )
                            }
                            true
                        }
                    )

                    SwipeToDismiss(
                        state = dismissState,
                        background = {
                            val color = when (dismissState.dismissDirection) {
                                DismissDirection.StartToEnd -> Color.Transparent
                                DismissDirection.EndToStart -> Color.Red
                                null -> Color.Transparent
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        color = color,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .padding(8.dp),
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.align(Alignment.CenterEnd)
                                )
                            }

                        },
                        directions = setOf(DismissDirection.EndToStart)
                    ) {
                        SingleListItem(
                            singleList = list,
                            modifier = Modifier
                                .fillMaxWidth()
                                .combinedClickable(onClick = {
                                    viewModel.onEvent(
                                        ListsEvent.GiffMeDaNextOne(
                                            list
                                        )
                                    )
                                }, onLongClick = {
                                    navController.navigate(
                                        Screen.AddEditSingleListScreen.route +
                                                "?listId=${list.id}&listColor=${list.color}"
                                    )
                                })
                        )
                    }


                    Spacer(Modifier.height(16.dp))

                }

            }
        }
    }
}