package com.example.giffmedanextone.feature_item.presentation.add_edit_single_list

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.draw.alpha
import com.example.giffmedanextone.R
import com.example.giffmedanextone.feature_item.domain.model.SingleList
import com.example.giffmedanextone.feature_item.presentation.add_edit_single_list.components.SingleContent
import com.example.giffmedanextone.feature_item.presentation.add_edit_single_list.components.TransparentHintTextField
import com.example.giffmedanextone.feature_item.presentation.util.colorPicker
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddEditSingleListScreen(
    navController: NavController,
    listColor: Int,
    viewModel: AddEditSingleListViewModel = hiltViewModel()
) {
    val titleState = viewModel.listTitle.value
    val currentItemState = viewModel.listCurrentItem.value
    val currentListSnapshotState = viewModel.currentList

    val scaffoldState = rememberScaffoldState()

    val listBackgroundAnimatable = remember {
        Animatable(
            Color(if (listColor != -1) listColor else viewModel.listColor.value).colorPicker
        )
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                AddEditSingleListViewModel.UIEvent.SaveCurrentList -> {
                    navController.navigateUp()
                }
                is AddEditSingleListViewModel.UIEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                AddEditSingleListViewModel.UIEvent.AddEntryToList -> {


                }
            }

        }

    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onEvent(AddEditSingleListEvent.SaveList) },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = stringResource(R.string.content_description_save_button)
                )
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(listBackgroundAnimatable.value)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {

                //Title
                TransparentHintTextField(
                    text = titleState.text,
                    hint = titleState.hint,
                    onValueChange = {
                        viewModel.onEvent(AddEditSingleListEvent.EnteredTitle(it))
                    },
                    onFocusChange = {
                        viewModel.onEvent(AddEditSingleListEvent.ChangeTitleFocus(it))
                    },
                    isHintVisible = titleState.isHintVisible,
                    singleLine = true,
                    textStyle = MaterialTheme.typography.h5
                )
                //User input
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    elevation = 5.dp,
                    backgroundColor = Color.White
                ) {
                    TransparentHintTextField(
                        text = currentItemState.text,
                        hint = currentItemState.hint,
                        onValueChange = {
                            viewModel.onEvent(AddEditSingleListEvent.EnteredContent(it))
                        },
                        onFocusChange = {
                            viewModel.onEvent(AddEditSingleListEvent.ChangeContentFocus(it))
                        },
                        isHintVisible = currentItemState.isHintVisible,
                        singleLine = true,
                        textStyle = MaterialTheme.typography.body1
                    )
                    Row(horizontalArrangement = Arrangement.End) {
                        IconButton(
                            onClick = {
                                viewModel.onEvent(AddEditSingleListEvent.AddEntryToList)
                            },
                            modifier = Modifier.alpha(
                                if (currentItemState.text.isBlank()) {
                                    0f
                                } else {
                                    1f
                                }
                            )
                        ) {
                            Icon(
                                imageVector = if (currentItemState.text.isBlank()) {
                                    Icons.Default.Edit
                                } else {
                                    Icons.Default.Check
                                },
                                contentDescription = stringResource(R.string.button_add_single_item_description)
                            )
                        }

                    }

                }

            }
            //colors to choose
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(listBackgroundAnimatable.value)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SingleList.listColors.forEach { color ->
                    val colorInt = color.toArgb()
                    val colorIntBackground = color.colorPicker.toArgb()
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 3.dp,
                                color = if (viewModel.listColor.value == colorInt) Color.Black
                                else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    listBackgroundAnimatable.animateTo(
                                        targetValue = Color(colorIntBackground),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                viewModel.onEvent(AddEditSingleListEvent.ChangeColor(colorInt))
                            }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(currentListSnapshotState) { itemName ->
                    SingleContent(
                        contentText = itemName,
                        color = viewModel.listColor.value,
                        onDeleteClicked = {
                            viewModel.onEvent(AddEditSingleListEvent.DeleteEntryFromList(itemName))
                        }
                    )

                }

            }

        }

    }

}