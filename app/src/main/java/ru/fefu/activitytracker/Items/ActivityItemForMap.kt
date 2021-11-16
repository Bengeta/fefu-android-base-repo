package ru.fefu.activitytracker.Items

import androidx.annotation.DrawableRes

data class ActivityItemForMap(
    val type: String?,
    val record:String?,
    var isSelected: Boolean = false,
)