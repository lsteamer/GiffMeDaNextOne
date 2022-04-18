package com.example.giffmedanextone.feature_item.data.repository

import com.example.giffmedanextone.feature_item.data.data_source.ListsDao
import com.example.giffmedanextone.feature_item.domain.model.SingleList
import com.example.giffmedanextone.feature_item.domain.repository.ListsRepository
import kotlinx.coroutines.flow.Flow

class ListsRepositoryImpl(
    private val dao: ListsDao
) : ListsRepository {
    override fun getLists(): Flow<List<SingleList>> = dao.getLists()

    override suspend fun insertList(list: SingleList) = dao.insertList(list = list)

    override suspend fun deleteList(list: SingleList) = dao.deleteList(list = list)
}