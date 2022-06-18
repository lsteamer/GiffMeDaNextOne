package com.anjegonz.giffmedanextone.feature_item.presentation.add_edit_single_list

import androidx.compose.ui.focus.FocusState

sealed class AddEditSingleListEvent {
    data class EnteredTitle(val value: String) : AddEditSingleListEvent()
    data class ChangeTitleFocus(val focusState: FocusState) : AddEditSingleListEvent()
    data class EnteredContent(val value: String) : AddEditSingleListEvent()
    data class ChangeContentFocus(val focusState: FocusState) : AddEditSingleListEvent()
    data class ChangeColor(val color: Int) : AddEditSingleListEvent()
    data class DeleteEntryFromList(val entry: String) : AddEditSingleListEvent()
    object AddEntryToList : AddEditSingleListEvent()
    object SaveList : AddEditSingleListEvent()


}
