package com.anjegonz.giffmedanextone.feature_item.data.repository

import com.anjegonz.giffmedanextone.feature_item.data.data_source.ListsDao
import com.anjegonz.giffmedanextone.feature_item.domain.model.InvalidListException
import com.anjegonz.giffmedanextone.feature_item.domain.model.SingleList
import com.anjegonz.giffmedanextone.feature_item.domain.repository.ListsRepository
import kotlinx.coroutines.flow.Flow

class ListsRepositoryImpl(
    private val dao: ListsDao
) : ListsRepository {

    override fun getLists(): Flow<List<SingleList>> = dao.getLists()

    override suspend fun getSingleListById(id: Int): SingleList = dao.getSingleListById(id)

    @Throws(InvalidListException::class)
    override suspend fun insertList(list: SingleList) {
        if(list.title.isBlank()){
            throw InvalidListException("The title of the list can't be blank")
        }
        if(list.bareList.isEmpty()){
            throw InvalidListException("The list can't be empty")
        }
        if(list.bareList.size==1){
            throw InvalidListException("List has to have more than one item")
        }
        dao.insertList(list = list)
    }

    override suspend fun deleteList(list: SingleList) = dao.deleteList(list = list)
}