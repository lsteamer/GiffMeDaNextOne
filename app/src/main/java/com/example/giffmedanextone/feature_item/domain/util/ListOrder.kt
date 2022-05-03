package com.example.giffmedanextone.feature_item.domain.util

sealed class ListOrder(val orderType: OrderType) {
    class Created(orderType: OrderType) : ListOrder(orderType)
    class LastAccessed(orderType: OrderType) : ListOrder(orderType)
    class Color(orderType: OrderType) : ListOrder(orderType)

    fun copy(orderType: OrderType): ListOrder = when (this) {
        is Color -> Color(orderType)
        is Created -> Created(orderType)
        is LastAccessed -> LastAccessed(orderType)
    }
}
