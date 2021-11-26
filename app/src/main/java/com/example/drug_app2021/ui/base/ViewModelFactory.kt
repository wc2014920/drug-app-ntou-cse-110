package com.example.drug_app2021.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.drug_app2021.data.repository.AuthRepository
import com.example.drug_app2021.data.repository.BaseRepository
import com.example.drug_app2021.data.repository.UserRepository
import com.example.drug_app2021.ui.auth.AuthViewModel
import com.example.drug_app2021.ui.home.HomeViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val repository: BaseRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> AuthViewModel(repository as AuthRepository) as T
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(repository as UserRepository) as T
            //add new ViewModel choose here
            else -> throw IllegalArgumentException("ViewModelClass not found")
        }
    }
}