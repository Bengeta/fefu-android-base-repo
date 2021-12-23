package ru.fefu.activitytracker.DataBaseStaff

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CrossItemDao {
    @Query("SELECT * FROM activities ORDER BY date_start DESC")
    fun getAll(): LiveData<List<CrossItemEntity>>

    @Query("SELECT * FROM activities where id = (SELECT MAX(id) FROM activities)")
    fun getLast(): CrossItemEntity

    @Query("SELECT * FROM activities where id = (SELECT MAX(id) FROM activities)")
    fun getLastInTime(): LiveData<CrossItemEntity>

    @Query("SELECT * FROM activities where id =:id")
    fun getById(id:Int): CrossItemEntity

    @Query("SELECT MAX(id) FROM activities")
    fun getLastId(): Int

    @Update
    fun UpdatLast(vararg CrossItemEntity: CrossItemEntity);

    @Insert
    fun insertCross(vararg CrossItemEntity: CrossItemEntity)

}