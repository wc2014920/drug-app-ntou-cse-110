package com.example.drug_app2021.data.db.entity

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface AlarmlistDao {
    //鬧鐘列表
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savealarmlist(alarmlist: List<Alarmlist>)

    @Query("SELECT * FROM alarmlist ORDER BY id ASC")
    fun readAlarmlistData() : LiveData<List<Alarmlist>>

    @Query("DELETE FROM alarmlist")
    suspend fun clearalarmlist()

}