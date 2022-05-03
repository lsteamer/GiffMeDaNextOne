package com.example.giffmedanextone.feature_item.presentation.lists

import com.example.giffmedanextone.feature_item.domain.model.SingleList
import com.example.giffmedanextone.feature_item.domain.util.ListOrder

sealed class ListsEvent {
    data class Order(val listOrder: ListOrder) : ListsEvent()
    data class DeleteList(val list: SingleList): ListsEvent()
    data class GiffMeDaNextOne(val list: SingleList) : ListsEvent()
    object RestoreList: ListsEvent()
    object ToggleOrderSection: ListsEvent()
}
