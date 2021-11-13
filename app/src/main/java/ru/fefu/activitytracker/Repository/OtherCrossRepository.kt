package ru.fefu.activitytracker.Repository

import ru.fefu.activitytracker.Items.CrossItem

class OtherCrossRepository {
    private val hardCoodeCrosses = listOf<CrossItem>(
        CrossItem(
            "","Вчера","","", "","Старт 14:49 | Финиш 16:31"
        ),
        CrossItem(
            "14.32km","2 часа 46 минут","Серфинг","@van_darkholme", "14 часов назад","Старт 14:49 | Финиш 16:31"
        ),
        CrossItem(
            "228 м","14 часов 48 минут","Качели","@techniquepasha", "14 часов назад","Старт 14:49 | Финиш 16:31"
        ),
        CrossItem(
            "10 км","1 час 10 минут","Езда на кадилак","@morgen_shtern", "14 часов назад","Старт 14:49 | Финиш 16:31"
        ),
    )

    fun getCrosses():List<CrossItem> = hardCoodeCrosses
}