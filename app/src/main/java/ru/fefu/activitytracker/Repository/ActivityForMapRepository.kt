package ru.fefu.activitytracker.Repository

import ru.fefu.activitytracker.Enums.CrossType
import ru.fefu.activitytracker.Items.ActivityItemForMap


class ActivityForMapRepository {
    private val hardCodeActivies = listOf<ActivityItemForMap>(
        ActivityItemForMap(CrossType.WALK.ordinal,"14km"),
        ActivityItemForMap(CrossType.BICYCLE.ordinal,"4km"),
        ActivityItemForMap(CrossType.RUNNING.ordinal,"144km")
    )

    fun getCrosses():List<ActivityItemForMap> = hardCodeActivies
}