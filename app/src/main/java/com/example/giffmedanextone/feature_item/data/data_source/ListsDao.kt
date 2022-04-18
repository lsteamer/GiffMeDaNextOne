package com.example.giffmedanextone.feature_item.data.data_source

import androidx.room.*
import com.example.giffmedanextone.feature_item.domain.model.SingleList
import kotlinx.coroutines.flow.Flow


@Dao
interface ListsDao {

    @Query("SELECT * FROM singlelist")
    fun getLists() : Flow<List<SingleList>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList()

    @Delete
    suspend fun deleteList(list: SingleList)
}