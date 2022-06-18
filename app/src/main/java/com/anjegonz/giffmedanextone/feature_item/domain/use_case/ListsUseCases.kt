package com.anjegonz.giffmedanextone.feature_item.domain.use_case

data class ListsUseCases(
    val getListsUseCase: GetListsUseCase,
    val deleteListUseCase: DeleteListUseCase,
    val addListUseCase: AddListUseCase,
    val getSingleListUseCase : GetSingleListUseCase,
)