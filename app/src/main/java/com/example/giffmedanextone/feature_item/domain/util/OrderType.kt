package com.example.giffmedanextone.feature_item.domain.util

sealed class OrderType{
    object Ascending: OrderType()
    object Descending: OrderType()
}
