package com.anjegonz.giffmedanextone.feature_item.domain.use_case

import com.anjegonz.giffmedanextone.feature_item.domain.model.SingleList
import com.anjegonz.giffmedanextone.feature_item.domain.repository.ListsRepository

class DeleteListUseCase(
    private val repository: ListsRepository
) {

    suspend operator fun invoke(list: SingleList) {
        repository.deleteList(list)
    }
}