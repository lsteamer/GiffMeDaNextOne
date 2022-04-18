package com.example.giffmedanextone.feature_item.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.giffmedanextone.ui.theme.*
import kotlin.collections.List

@Entity
data class SingleList(
    val title: String,
    val currentItem: String,
    val bareList: List<String>,
    val accumulatingList: List<String>,
    val color: Int,
    @PrimaryKey val id: Int? = null
){
    companion object{
        val listColors = listOf(CyanPink, IntenseRed, LivelyOrange, CoolYellow, CalmGreen)
    }
}
