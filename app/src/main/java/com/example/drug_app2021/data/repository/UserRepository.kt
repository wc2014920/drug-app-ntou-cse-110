package com.example.drug_app2021.data.repository

import androidx.lifecycle.LiveData
import com.example.drug_app2021.data.db.AppDatabase
import com.example.drug_app2021.data.db.entity.Alarmlist
import com.example.drug_app2021.data.db.entity.Alarmlistitem
import com.example.drug_app2021.data.db.entity.User
import com.example.drug_app2021.data.network.UserApi
import com.example.drug_app2021.data.response.Data
import com.example.drug_app2021.data.response.Detail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.max

class UserRepository(
    private val api: UserApi,
    private val db: AppDatabase
) : BaseRepository(){

    suspend fun convertDataTypeAndSave (it: List<Data>, it2 : List<Detail>) {
        val list: MutableList<Alarmlist> = mutableListOf()
        val item: MutableList<Alarmlistitem> = mutableListOf()
        val size = max(it.size, it2.size)
        for ( i in 0 until size) {
            if( i < it.size ) {// i >= it.size ignore
                list += Alarmlist(
                    id = it[i].id,
                    clinic = it[i].clinic,
                    clinic_id = it[i].clinic_id,
                    created_at = it[i].created_at,
                    doctor_id = it[i].doctor_id,
                    doctor_name = it[i].doctor_name,
                    expired_date = it[i].expired_date,
                    patient_id = it[i].patient_id,
                    patient_name = it[i].patient_name,
                    updated_at = it[i].updated_at
                )
            }
            if( i < it2.size) {// i >= it2.size ignore
                item += Alarmlistitem(
                    id = it2[i].id,
                    prescription_id = it2[i].prescription_id,
                    patient_id = it2[i].patient_id,
                    drug_name = it2[i].drug_name,
                    drug_pic_url = it2[i].drug_pic_url,
                    drug_code = it2[i].drug_code,
                    drug_amount = it2[i].drug_amount,
                    day = it2[i].day,
                    time_morning = it2[i].time_morning,
                    time_night = it2[i].time_night,
                    time_noon = it2[i].time_noon,
                    time_sleep = it2[i].time_sleep,
                    created_at = it2[i].created_at,
                    updated_at = it2[i].updated_at,
                )
            }
        }
        saveAlarmList(list, item)
    }

    suspend fun getAlarmList() = safeApiCall {
        api.getAlarmList()
    }
    suspend fun getUserData() : User = db.getUserDao().readUserData()

    val readAllAlarmlist: LiveData<List<Alarmlist>> = db.getAlarmlistDao().readAlarmlistData()

    suspend fun readAlarmlistitem(id: String): List<Alarmlistitem> = db.getAlarmlistitemDao().readAlarmListItem(id)

    suspend fun readAlarmListItemAll(): List<Alarmlistitem> = db.getAlarmlistitemDao().readAlarmListItemALl()

    suspend fun saveAlarmList(alarmList: List<Alarmlist>, Alarmlistitem: List<Alarmlistitem>) {
        db.getAlarmlistDao().clearalarmlist()
        db.getAlarmlistitemDao().clearalarmlistitem()
        db.getAlarmlistDao().savealarmlist(alarmList)
        db.getAlarmlistitemDao().savealarmlistitem(Alarmlistitem)
    }

}