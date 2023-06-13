package com.example.courses.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class TopicData(
    @StringRes val title: Int,
    val count: Int,
    @DrawableRes val image: Int,
)
