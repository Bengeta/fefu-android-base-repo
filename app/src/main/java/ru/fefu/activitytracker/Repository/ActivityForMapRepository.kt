package ru.fefu.activitytracker.Repository

import ru.fefu.activitytracker.Items.ActivityItemForMap


class ActivityForMapRepository {
    private val hardCodeActivies = listOf<ActivityItemForMap>(
        ActivityItemForMap("Серфинг","14km"),ActivityItemForMap("Велосипед","4km"),ActivityItemForMap("Бег","144km")
    )

    fun getCrosses():List<ActivityItemForMap> = hardCodeActivies
}