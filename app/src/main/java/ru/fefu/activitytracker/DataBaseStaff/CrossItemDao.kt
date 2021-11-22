package ru.fefu.activitytracker.DataBaseStaff

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CrossItemDao {
    @Query("SELECT * FROM activities")
    fun getAll(): LiveData<List<CrossItemEntity>>

    @Query("SELECT * FROM activities")
    fun getAll_(): List<CrossItemEntity>

    @Insert
    fun insertCross(vararg CrossItemEntity: CrossItemEntity)

}