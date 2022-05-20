package com.example.giffmedanextone.feature_item.domain.use_case

import com.example.giffmedanextone.feature_item.domain.model.SingleList
import com.example.giffmedanextone.feature_item.domain.repository.ListsRepository

class GetSingleListUseCase(private val repository: ListsRepository) {
    suspend operator fun invoke(id: Int): SingleList? = repository.getSingleListById(id)
}