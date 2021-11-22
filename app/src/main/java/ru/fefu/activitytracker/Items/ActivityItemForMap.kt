package ru.fefu.activitytracker.Items

import androidx.annotation.DrawableRes

data class ActivityItemForMap(
    val type: Int,
    val record:String?,
    var isSelected: Boolean = false,
)