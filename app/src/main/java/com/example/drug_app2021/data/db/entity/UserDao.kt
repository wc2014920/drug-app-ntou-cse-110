package com.example.drug_app2021.data.db.entity

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    suspend fun readUserData() : User


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(user:User)
    
    @Delete
    suspend fun delete(user:User)
    
    @Update
    suspend fun update(user:User)
}