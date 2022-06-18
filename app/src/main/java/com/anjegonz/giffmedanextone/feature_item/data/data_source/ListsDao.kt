package com.anjegonz.giffmedanextone.feature_item.data.data_source

import androidx.room.*
import com.anjegonz.giffmedanextone.feature_item.domain.model.SingleList
import kotlinx.coroutines.flow.Flow


@Dao
interface ListsDao {

    @Query("SELECT * FROM singlelist")
    fun getLists() : Flow<List<SingleList>>

    @Query("SELECT * FROM singlelist WHERE id = :id")
    suspend fun getSingleListById(id: Int) : SingleList

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(list: SingleList)

    @Delete
    suspend fun deleteList(list: SingleList)
}