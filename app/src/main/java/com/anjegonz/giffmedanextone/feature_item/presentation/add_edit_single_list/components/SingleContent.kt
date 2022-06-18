package com.anjegonz.giffmedanextone.feature_item.presentation.add_edit_single_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.Text
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anjegonz.giffmedanextone.R
import com.anjegonz.giffmedanextone.feature_item.domain.model.SingleList


@Composable
fun SingleContent(
    modifier: Modifier = Modifier,
    contentText: String = "Item #1",
    color: Int = SingleList.listColors.random().toArgb(),
    onDeleteClicked: () -> Unit,
    cornerRadius: Dp = 99.dp,
    elevation: Dp = 5.dp
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(cornerRadius),
        elevation = elevation,
        backgroundColor = Color(color)
    ) {
        Text(
            text = contentText,
            style = TextStyle(color = Color.Black, fontSize = 16.sp),
            modifier = modifier.padding(vertical = 10.dp),
            textAlign = TextAlign.Center
        )
        Row(
            horizontalArrangement = Arrangement.End,
        ) {
            IconButton(onClick = {
                onDeleteClicked.invoke()
            }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.content_description_save_button)
                )
            }
        }

    }
}

@Preview
@Composable
private fun SingleContentPreview() {
    SingleContent(onDeleteClicked = {})
}