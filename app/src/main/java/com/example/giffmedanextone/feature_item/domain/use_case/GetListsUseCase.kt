package com.example.giffmedanextone.feature_item.domain.use_case

import com.example.giffmedanextone.feature_item.domain.model.SingleList
import com.example.giffmedanextone.feature_item.domain.repository.ListsRepository
import com.example.giffmedanextone.feature_item.domain.util.ListOrder
import com.example.giffmedanextone.feature_item.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetListsUseCase(
    private val repository: ListsRepository
) {

    operator fun invoke(
        listOrder: ListOrder = ListOrder.LastAccessed(OrderType.Descending)
    ): Flow<List<SingleList>> {
        return repository.getLists().map { lists ->
            when (listOrder.orderType) {
                is OrderType.Ascending -> {
                    when(listOrder){
                        is ListOrder.LastAccessed -> lists.sortedBy { it.timeLastAccessed }
                        is ListOrder.Created -> lists.sortedBy { it.timeCreated }
                        is ListOrder.Color -> lists.sortedBy { it.color }
                    }
                }
                is OrderType.Descending -> {
                    when(listOrder){
                        is ListOrder.LastAccessed -> lists.sortedByDescending { it.timeLastAccessed }
                        is ListOrder.Created -> lists.sortedByDescending { it.timeCreated }
                        is ListOrder.Color -> lists.sortedByDescending { it.color }
                    }
                }
            }
        }
    }
}