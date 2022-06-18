package com.anjegonz.giffmedanextone.feature_item.presentation.lists

import com.anjegonz.giffmedanextone.feature_item.domain.model.SingleList
import com.anjegonz.giffmedanextone.feature_item.domain.util.ListOrder
import com.anjegonz.giffmedanextone.feature_item.domain.util.OrderType

data class ListsState(
    val lists: List<SingleList> = emptyList(),
    val listsOrder: ListOrder = ListOrder.LastAccessed(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
