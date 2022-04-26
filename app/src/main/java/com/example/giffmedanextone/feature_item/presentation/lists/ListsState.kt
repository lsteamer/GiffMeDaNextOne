package com.example.giffmedanextone.feature_item.presentation.lists

import com.example.giffmedanextone.feature_item.domain.model.SingleList
import com.example.giffmedanextone.feature_item.domain.util.ListOrder
import com.example.giffmedanextone.feature_item.domain.util.OrderType

data class ListsState(
    val lists: List<SingleList> = emptyList(),
    val listsOrder: ListOrder = ListOrder.LastAccessed(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
