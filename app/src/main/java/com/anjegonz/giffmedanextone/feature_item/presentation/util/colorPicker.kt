package com.anjegonz.giffmedanextone.feature_item.presentation.util

import androidx.compose.ui.graphics.Color
import com.anjegonz.giffmedanextone.ui.theme.*

inline val Color.colorPicker
    get() = when (this) {
        CyanPink -> CyanPinkBackground
        IntenseRed -> IntenseRedBackground
        LivelyOrange -> LivelyOrangeBackground
        CoolYellow -> CoolYellowBackground
        CalmGreen -> CalmGreenBackground
        else -> Teal200
    }

inline val Long.colorPickerLong
    get() = when(this){
        0xFFA7226E ->0xFFDBA6C5
        0xFFEC2049 ->0xFFF37991
        0xFFF26B38 ->0xFFF7A687
        0xFFF7DB4F ->0xFFFAE995
        0xFF2F9599 ->0xFF82BFC1
        else -> 0xFF03DAC5
    }