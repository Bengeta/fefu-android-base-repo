package ru.fefu.activitytracker.DataBaseStaff

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "activities")
@Serializable
data class CrossItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "type") val type: Int? = null,
    @ColumnInfo(name = "date_start") val date_start: Long? = null,
    @ColumnInfo(name = "date_end") val date_end: Long? = null,
    @ColumnInfo(name = "coordinates") val coordinates: String? = null,
    @ColumnInfo(name = "date") val date: String? = null
)

