package com.example.giffmedanextone.feature_item.presentation.lists

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giffmedanextone.feature_item.domain.model.SingleList
import com.example.giffmedanextone.feature_item.domain.use_case.ListsUseCases
import com.example.giffmedanextone.feature_item.domain.util.ListOrder
import com.example.giffmedanextone.feature_item.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
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

    init{
        getLists(ListOrder.LastAccessed(OrderType.Descending))
    }

    fun onEvent(event: ListsEvent) {
        when (event) {

            is ListsEvent.GiffMeDaNextOne -> {
                viewModelScope.launch {

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

}