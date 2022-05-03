package com.example.giffmedanextone.feature_item.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.giffmedanextone.feature_item.domain.model.SingleList

@Database(
    entities = [SingleList::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class ListsDataBase : RoomDatabase() {
    abstract val listsDao : ListsDao

    companion object {
        const val DATABASE_NAME = "lists_db"
    }
}