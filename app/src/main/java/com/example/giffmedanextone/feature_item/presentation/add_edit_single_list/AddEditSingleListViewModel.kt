package com.example.giffmedanextone.feature_item.presentation.add_edit_single_list

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giffmedanextone.R
import com.example.giffmedanextone.feature_item.domain.model.InvalidListException
import com.example.giffmedanextone.feature_item.domain.model.SingleList
import com.example.giffmedanextone.feature_item.domain.use_case.ListsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditSingleListViewModel @Inject constructor(
    private val listsUseCases: ListsUseCases,
    savedStateHandle: SavedStateHandle,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _listTitle = mutableStateOf(
        SingleListTextFieldState(
            hint = context.getString(R.string.give_title_hint)
        )
    )
    val listTitle: State<SingleListTextFieldState> = _listTitle

    private val _listCurrentItem = mutableStateOf(
        SingleListTextFieldState(
            hint = context.getString(R.string.give_content_hint)
        )
    )
    val listCurrentItem: State<SingleListTextFieldState> = _listCurrentItem

    private val _currentList = mutableStateOf<List<String>>(emptyList())
    val currentList: State<List<String>> = _currentList

    private val _listColor = mutableStateOf<Int>(SingleList.listColors.random().toArgb())
    val listColor: State<Int> = _listColor

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if(noteId != -1){
                viewModelScope.launch {
                    listsUseCases.getSingleListUseCase(noteId)?.also { singleList ->
                        currentNoteId = singleList.id
                        _listTitle.value = listTitle.value.copy(
                            text = singleList.title,
                            isHintVisible = false
                        )
                        _listCurrentItem.value = listCurrentItem.value.copy(
                            text = "",
                            hint = context.getString(R.string.give_next_content_hint),
                            isHintVisible = true
                        )
                        _currentList.value = singleList.bareList
                        _listColor.value = singleList.color

                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditSingleListEvent) {
        when (event) {
            is AddEditSingleListEvent.EnteredTitle -> {
                _listTitle.value = listTitle.value.copy(
                    text = event.value
                )
            }
            is AddEditSingleListEvent.ChangeTitleFocus -> {
                _listTitle.value = listTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused && listTitle.value.text.isBlank()
                )
            }
            is AddEditSingleListEvent.EnteredContent -> {
                _listCurrentItem.value = _listCurrentItem.value.copy(
                    text = event.value
                )
            }
            is AddEditSingleListEvent.ChangeContentFocus -> {
                _listCurrentItem.value = _listCurrentItem.value.copy(
                    isHintVisible = !event.focusState.isFocused && _listCurrentItem.value.text.isBlank()
                )
            }
            is AddEditSingleListEvent.ChangeColor -> {
                _listColor.value = event.color
            }
            AddEditSingleListEvent.AddContentToList -> {
                _currentList.value = _currentList.value.also { _listCurrentItem.value }
                _listCurrentItem.value = listCurrentItem.value.copy(
                    text = "",
                    hint = context.getString(R.string.give_next_content_hint),
                    isHintVisible = true
                )
            }
            AddEditSingleListEvent.SaveList -> {
                viewModelScope.launch {
                    try {
                        listsUseCases.addListUseCase(
                            SingleList(
                                title = listTitle.value.text,
                                currentItem = context.getString(R.string.first_current_item_label),
                                bareList = currentList.value,
                                accumulatingList = currentList.value,
                                color = listColor.value,
                                timeCreated = System.currentTimeMillis(),
                                timeLastAccessed = System.currentTimeMillis(),
                                id = currentNoteId
                            )
                        )
                        _eventFlow.emit(UIEvent.SaveCurrentList)
                    } catch (e: InvalidListException) {
                        _eventFlow.emit(
                            UIEvent.ShowSnackBar(
                                message = context.getString(R.string.list_error_message_label)
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UIEvent {
        data class ShowSnackBar(val message: String) : UIEvent()
        object SaveCurrentList : UIEvent()
    }

}