package ru.fefu.activitytracker.DataBaseStaff

import kotlinx.serialization.Serializable


sealed class DBCrossItem

@Serializable
class Activity(
    val id: Int?,
    val type: Int?,
    val date_start: Long?,
    val date_end: Long?,
    val coordinates: String?,
) : DBCrossItem()

class Date_(
    val date: String?
) : DBCrossItem()