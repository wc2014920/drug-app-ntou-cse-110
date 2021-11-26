package com.example.drug_app2021.data.db.entity

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AlarmlistitemDao {

    @Query("SELECT * FROM alarmlistitem WHERE prescription_id LIKE :id ORDER BY id ASC")
    suspend fun readAlarmListItem(id : String) : List<Alarmlistitem>

    @Query("SELECT * FROM alarmlistitem ORDER BY id ASC")
    suspend fun readAlarmListItemALl() : List<Alarmlistitem>
    //鬧鐘明細列表
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savealarmlistitem(Alarmlistitem: List<Alarmlistitem>)

    @Query("DELETE FROM alarmlistitem")
    suspend fun clearalarmlistitem()
}