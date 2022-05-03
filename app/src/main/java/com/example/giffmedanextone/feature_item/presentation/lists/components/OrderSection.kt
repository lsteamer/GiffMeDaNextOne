package com.example.giffmedanextone.feature_item.presentation.lists.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.giffmedanextone.R
import com.example.giffmedanextone.feature_item.domain.util.ListOrder
import com.example.giffmedanextone.feature_item.domain.util.OrderType

@Composable
fun OrderSection(
    modifier: Modifier,
    listOrder: ListOrder = ListOrder.LastAccessed(OrderType.Descending),
    onOrderChange: (ListOrder) -> Unit
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = stringResource(R.string.composable_radio_button_order_last_accessed),
                selected = listOrder is ListOrder.LastAccessed,
                onSelect = { onOrderChange(ListOrder.LastAccessed(listOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = stringResource(R.string.composable_radio_button_order_created),
                selected = listOrder is ListOrder.Created,
                onSelect = { onOrderChange(ListOrder.Created(listOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = stringResource(R.string.composable_radio_button_order_color),
                selected = listOrder is ListOrder.Color,
                onSelect = { onOrderChange(ListOrder.Color(listOrder.orderType)) }
            )
        }

        Spacer(modifier = Modifier.width(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = stringResource(R.string.composable_radio_button_order_ascending),
                selected = listOrder.orderType is OrderType.Ascending,
                onSelect = { onOrderChange(listOrder.copy(OrderType.Ascending)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = stringResource(R.string.composable_radio_button_order_descending),
                selected = listOrder.orderType is OrderType.Descending,
                onSelect = { onOrderChange(listOrder.copy(OrderType.Descending)) }
            )
            Spacer(modifier = Modifier.width(8.dp))

        }
    }

}