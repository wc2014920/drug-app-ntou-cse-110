package com.example.drug_app2021.ui.base

import androidx.lifecycle.ViewModel
import com.example.drug_app2021.data.network.UserApi
import com.example.drug_app2021.data.repository.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseViewModel (
    private val repository: BaseRepository
): ViewModel(){
    suspend fun logout(api: UserApi) = withContext(Dispatchers.IO){
        repository.logout(api)
    }
}