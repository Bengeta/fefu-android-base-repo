package ru.fefu.activitytracker.DataBaseStaff

import android.app.Application
import androidx.room.Room
import ru.fefu.activitytracker.DataBaseStaff.MyDataBase

class App : Application() {
    companion object {
        lateinit var INSTANCE: App
    }

    val db: MyDataBase by lazy {
        Room.databaseBuilder(
            this,
            MyDataBase::class.java,
            "my_database"
        ).allowMainThreadQueries().build()
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

}