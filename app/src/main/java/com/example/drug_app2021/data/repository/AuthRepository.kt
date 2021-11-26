package com.example.drug_app2021.data.repository

import androidx.lifecycle.LiveData
import com.example.drug_app2021.data.UserPreferences
import com.example.drug_app2021.data.db.AppDatabase
import com.example.drug_app2021.data.db.entity.User
import com.example.drug_app2021.data.network.Authapi


//import java.util.prefs.Preferences

class AuthRepository(
    private val api: Authapi,
    private val preferences: UserPreferences,
    private val db: AppDatabase
) : BaseRepository(){

    suspend fun login(
        email: String,
        password: String
    ) = safeApiCall {
        api.login(email, password)

    }

    suspend fun register(
        name: String,
        email: String,
        password: String,
        cellphone: String
    ) = safeApiCall {
        api.register(name, email, password, cellphone)

    }

    suspend fun saveAuthToken(token: String){
        preferences.saveAuthToken(token)
    }

//    val readUserData: LiveData<User> = db.getUserDao().readUserData()

    suspend fun saveUser(user: User) {
        db.getUserDao().upsert(user)
    }

//    val readUserData: LiveData<List<User>> = UserDao.readUserData()
}