package com.anjegonz.giffmedanextone.feature_item.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.anjegonz.giffmedanextone.ui.theme.*
import java.lang.Exception
import kotlin.collections.List

@Entity
data class SingleList(
    val title: String,
    val currentItem: String,
    val bareList: List<String>,
    val accumulatingList: List<String>,
    val color: Int,
    val timeCreated: Long,
    val timeLastAccessed: Long,
    @PrimaryKey val id: Int? = null
){
    companion object{
        val listColors = listOf(CyanPink, IntenseRed, LivelyOrange, CoolYellow, CalmGreen)
    }
}

class InvalidListException(message: String) : Exception(message)