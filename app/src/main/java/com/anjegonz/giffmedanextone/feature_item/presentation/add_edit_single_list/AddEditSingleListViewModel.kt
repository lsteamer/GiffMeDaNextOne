package com.anjegonz.giffmedanextone.feature_item.presentation.add_edit_single_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjegonz.giffmedanextone.feature_item.domain.model.InvalidListException
import com.anjegonz.giffmedanextone.feature_item.domain.model.SingleList
import com.anjegonz.giffmedanextone.feature_item.domain.use_case.ListsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditSingleListViewModel @Inject constructor(
    private val listsUseCases: ListsUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _listTitle = mutableStateOf(SingleListTextFieldState())
    val listTitle: State<SingleListTextFieldState> = _listTitle

    private val _listCurrentItem = mutableStateOf(SingleListTextFieldState())
    val listCurrentItem: State<SingleListTextFieldState> = _listCurrentItem

    private val _currentList = mutableStateListOf<String>()
    val currentList: SnapshotStateList<String> = _currentList

    private val _listColor = mutableStateOf(SingleList.listColors.random().toArgb())
    val listColor: State<Int> = _listColor

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("listId")?.let { noteId ->
            if (noteId != -1) {
                viewModelScope.launch {
                    listsUseCases.getSingleListUseCase(noteId)?.also { singleList ->
                        currentNoteId = singleList.id
                        _listTitle.value = listTitle.value.copy(
                            text = singleList.title,
                            isHintVisible = false
                        )
                        _listCurrentItem.value = listCurrentItem.value.copy(
                            text = "",
                            isHintVisible = true
                        )
                        _currentList.addAll(singleList.bareList)
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
            is AddEditSingleListEvent.AddEntryToList -> {
                if (_listCurrentItem.value.text.isNotBlank()) {
                    _currentList.add(_listCurrentItem.value.text)
                    _listCurrentItem.value = listCurrentItem.value.copy(
                        text = "",
                        isHintVisible = false
                    )
                    viewModelScope.launch {
                        _eventFlow.emit(UIEvent.AddEntryToList)
                    }
                }
            }
            is AddEditSingleListEvent.DeleteEntryFromList -> {
                _currentList.remove(event.entry)
            }
            is AddEditSingleListEvent.SaveList -> {
                viewModelScope.launch {
                    try {
                        listsUseCases.addListUseCase(
                            SingleList(
                                title = listTitle.value.text,
                                currentItem = "",
                                bareList = currentList,
                                accumulatingList = currentList,
                                color = listColor.value,
                                timeCreated = System.currentTimeMillis(),
                                timeLastAccessed = System.currentTimeMillis(),
                                id = currentNoteId
                            )
                        )
                        _eventFlow.emit(UIEvent.SaveCurrentList)
                    } catch (e: InvalidListException) {
                        _eventFlow.emit(
                            UIEvent.ShowErrorSnackBar
                        )
                    }
                }
            }
        }
    }

    sealed class UIEvent {
        object ShowErrorSnackBar : UIEvent()
        object SaveCurrentList : UIEvent()
        object AddEntryToList : UIEvent()
    }

}