package com.anjegonz.giffmedanextone.di

import android.app.Application
import androidx.room.Room
import com.anjegonz.giffmedanextone.feature_item.data.data_source.ListsDataBase
import com.anjegonz.giffmedanextone.feature_item.data.repository.ListsRepositoryImpl
import com.anjegonz.giffmedanextone.feature_item.domain.repository.ListsRepository
import com.anjegonz.giffmedanextone.feature_item.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideListsDatabase(app: Application): ListsDataBase {
        return Room.databaseBuilder(
            app,
            ListsDataBase::class.java,
            ListsDataBase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideListsRepository(db: ListsDataBase): ListsRepository {
        return ListsRepositoryImpl(db.listsDao)
    }

    @Provides
    @Singleton
    fun providesListsUseCases(repository: ListsRepository): ListsUseCases {
        return ListsUseCases(
            getListsUseCase = GetListsUseCase(repository),
            deleteListUseCase = DeleteListUseCase(repository),
            addListUseCase = AddListUseCase(repository),
            getSingleListUseCase = GetSingleListUseCase(repository)
        )
    }
}