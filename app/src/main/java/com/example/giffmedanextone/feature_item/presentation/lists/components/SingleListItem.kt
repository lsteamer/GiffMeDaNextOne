package com.example.giffmedanextone.feature_item.presentation.lists.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.giffmedanextone.feature_item.domain.model.SingleList

@Composable
fun SingleListItem(
    singleList: SingleList = SingleList(
        "Movie",
        "Matrix",
        emptyList(),
        emptyList(),
        android.graphics.Color.DKGRAY,
        100,
        100
    ),
    modifier: Modifier = Modifier.fillMaxWidth(),
    cornerRadius: Dp = 10.dp,
    elevation: Dp = 5.dp
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(cornerRadius),
        elevation = elevation,
        backgroundColor = Color.Cyan
    ) {
        Column(
            modifier
                .padding(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = singleList.title,
                style = TextStyle(color = Color.Black, fontSize = 16.sp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = singleList.currentItem,
                style = TextStyle(color = Color.Black, fontSize = 16.sp)
            )
        }

    }
}

@Preview
@Composable
private fun SingleListItemPreview() {
    SingleListItem()
}