package com.example.giffmedanextone.feature_item.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.giffmedanextone.feature_item.domain.model.SingleList

@Database(
    entities = [SingleList::class],
    version = 1
)

abstract class ListDataBase : RoomDatabase() {
    abstract val listsDao : ListsDao
}