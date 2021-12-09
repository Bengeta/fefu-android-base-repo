package ru.fefu.activitytracker.Repository

import ru.fefu.activitytracker.Items.CrossItem

class MyCrossRepository {
    private val hardCoodeCrosses = listOf<CrossItem>(
        CrossItem(
            "", "Вчера", "", "", "", "Старт 14:49 | Финиш 16:31"
        ),
        CrossItem(
            "14.32km",
            "2 часа 46 минут",
            "Серфинг",
            "",
            "14 часов назад",
            "Старт 14:49 | Финиш 16:31"
        ),
        CrossItem(
            "", "Май 2022 года", "", "", "", "Старт 14:49 | Финиш 16:31"
        ),
        CrossItem(
            "1 000 м",
            "60 минут",
            "Велосипед  \uD83D\uDEB2",
            "",
            "29.05.2022",
            "Старт 14:49 | Финиш 16:31"
        ),
    )

    fun getCrosses(): List<CrossItem> = hardCoodeCrosses
}