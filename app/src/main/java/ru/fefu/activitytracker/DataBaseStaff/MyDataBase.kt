package ru.fefu.activitytracker.DataBaseStaff

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [CrossItemEntity::class], version = 1)
abstract class MyDataBase : RoomDatabase() {
    abstract fun activityDao(): CrossItemDao
}
