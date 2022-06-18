package com.anjegonz.giffmedanextone.feature_item.presentation.lists

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjegonz.giffmedanextone.feature_item.domain.model.InvalidListException
import com.anjegonz.giffmedanextone.feature_item.domain.model.SingleList
import com.anjegonz.giffmedanextone.feature_item.domain.use_case.ListsUseCases
import com.anjegonz.giffmedanextone.feature_item.domain.util.ListOrder
import com.anjegonz.giffmedanextone.feature_item.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListsViewModel @Inject constructor(
    private val listsUseCases: ListsUseCases
) : ViewModel() {

    private val _state = mutableStateOf(ListsState())
    val state: State<ListsState> = _state

    private var recentlyDeletedList: SingleList? = null

    private var getListsJob: Job? = null

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getLists(ListOrder.LastAccessed(OrderType.Descending))
    }

    fun onEvent(event: ListsEvent) {
        when (event) {
            is ListsEvent.GiffMeDaNextOne -> {

                val newCurrentItem = event.list.accumulatingList.random()

                val newCurrentAccumulatingList: MutableList<String> =
                    (event.list.accumulatingList + event.list.bareList).toMutableList()

                while (newCurrentAccumulatingList.contains(newCurrentItem)) {
                    newCurrentAccumulatingList.remove(newCurrentItem)
                }

                val newCurrentSingleList = SingleList(
                    title = event.list.title,
                    currentItem = newCurrentItem,
                    bareList = event.list.bareList,
                    accumulatingList = newCurrentAccumulatingList,
                    color = event.list.color,
                    timeCreated = event.list.timeCreated,
                    timeLastAccessed = System.currentTimeMillis(),
                    id = event.list.id
                )

                viewModelScope.launch {
                    try {
                        listsUseCases.addListUseCase(
                            newCurrentSingleList
                        )

                    } catch (e: InvalidListException) {
                        _eventFlow.emit(
                            UIEvent.ShowErrorSnackBar
                        )
                    }
                }
            }

            is ListsEvent.Order -> {
                if (
                    state.value.listsOrder::class == event.listOrder::class &&
                    state.value.listsOrder.orderType == event.listOrder.orderType
                ) {
                    return
                }
                getLists(event.listOrder)
            }
            is ListsEvent.DeleteList -> {
                viewModelScope.launch {
                    listsUseCases.deleteListUseCase(event.list)
                    recentlyDeletedList = event.list
                }
            }
            is ListsEvent.RestoreList -> {
                viewModelScope.launch {
                    listsUseCases.addListUseCase(recentlyDeletedList ?: return@launch)
                    recentlyDeletedList = null
                }
            }
            is ListsEvent.ToggleOrderSection -> {
                _state.value = _state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getLists(listOrder: ListOrder) {
        getListsJob?.cancel()
        getListsJob = listsUseCases.getListsUseCase(listOrder)
            .onEach { lists ->
                _state.value = state.value.copy(
                    lists = lists,
                    listsOrder = listOrder
                )
            }
            .launchIn(viewModelScope)
    }

    sealed class UIEvent {
        object ShowErrorSnackBar : UIEvent()
    }

}