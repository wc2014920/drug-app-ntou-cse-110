package com.example.drug_app2021.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.drug_app2021.data.Coroutines
import com.example.drug_app2021.data.db.entity.Alarmlist
import com.example.drug_app2021.data.db.entity.Alarmlistitem
import com.example.drug_app2021.data.network.Resource
import com.example.drug_app2021.data.repository.UserRepository
import com.example.drug_app2021.data.response.Data
import com.example.drug_app2021.data.response.Detail
import com.example.drug_app2021.data.response.ListResponse
import com.example.drug_app2021.ui.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.math.max

class HomeViewModel (
    private val repository: UserRepository
    ): BaseViewModel(repository){
    private val _alarmlist: MutableLiveData<Resource<ListResponse>> = MutableLiveData()
    val alarmList: MutableLiveData<Resource<ListResponse>>
        get() = _alarmlist
    private lateinit var job: Job
    fun getAlarmList() {
        job = Coroutines.ioTheMain(
            {repository.getAlarmList()},
            {
                _alarmlist.value = it
            }
        )
    }
    suspend fun insertAlarmlisttoDB(it: List<Data>, it2 : List<Detail>) {
        repository.convertDataTypeAndSave(it, it2)
    }

    override fun onCleared() {
        super.onCleared()
        if(::job.isInitialized) job.cancel()
    }




    val readAlarmList : LiveData<List<Alarmlist>> = repository.readAllAlarmlist
    suspend fun readAlarmlistitem(prescription_id: String) : List<Alarmlistitem> = repository.readAlarmlistitem(prescription_id)

    suspend fun readAlarmlistitemAll() : List<Alarmlistitem> = readAlarmlistitemAll()

    suspend fun saveAlarmList(
        alarmList: List<com.example.drug_app2021.data.db.entity.Alarmlist>,
        Alarmlistitem: List<Alarmlistitem>)
    {
        repository.saveAlarmList(alarmList, Alarmlistitem)
    }

    /*
    * viewModelScope.launch {
        _alarmlist.value = Resource.Loading
        _alarmlist.value = repository.getAlarmList()
        Log.d("_alarmlist",_alarmlist.value.toString())
        Log.d("_alarmlist",_alarmlist.value.toString())
    }*/
}