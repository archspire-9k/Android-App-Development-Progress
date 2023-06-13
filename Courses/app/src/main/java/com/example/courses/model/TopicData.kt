package com.example.courses.model

import androidx.annotation.DrawableRes

data class TopicData(
    @DrawableRes val image: Int,
    val title: String,
    val count: Int
)
