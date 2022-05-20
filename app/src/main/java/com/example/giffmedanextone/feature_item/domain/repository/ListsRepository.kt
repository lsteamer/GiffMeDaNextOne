package com.example.giffmedanextone.feature_item.domain.repository

import com.example.giffmedanextone.feature_item.domain.model.SingleList
import kotlinx.coroutines.flow.Flow

interface ListsRepository {

    fun getLists(): Flow<List<SingleList>>

    suspend fun getSingleListById(id: Int): SingleList?

    suspend fun insertList(list: SingleList)

    suspend fun deleteList(list: SingleList)
}