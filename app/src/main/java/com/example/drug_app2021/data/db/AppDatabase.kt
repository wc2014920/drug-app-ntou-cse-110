package com.example.drug_app2021.data.db

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.drug_app2021.data.db.convert.Converters
import com.example.drug_app2021.data.db.entity.*

@Database(
    entities = [User::class,Alarmlist::class,Alarmlistitem::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun getUserDao() : UserDao
    abstract fun getAlarmlistDao() : AlarmlistDao
    abstract fun getAlarmlistitemDao() : AlarmlistitemDao

    companion object{

        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            if(instance != null) {
                return instance!!
            }
            instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "Medicare_Database" //Schema Database's name
            )
                .fallbackToDestructiveMigration()
                .build()
            return instance!!
        }



    }
//
//    private class MIGRATION_2_3 : Migration(2, 3){
//        override fun migrate(database: SupportSQLiteDatabase) {
//            database.execSQL(
//                "CREATE TABLE IF NOT EXISTS `alarmlist` (`id` INTEGER , `clinic` TEXT, clinic_id INTEGER, created_at TEXT, doctor_id INTEGER, doctor_name TEXT, expired_name TEXT, patient_id INTEGER, patient_name TEXT, updated_at TEXT, PRIMARY KEY(`id`))")
//        }
//    }
//
//    private class MIGRATION_1_2 : Migration(1, 2){
//        override fun migrate(database: SupportSQLiteDatabase) {
//            database.execSQL(
//                "CREATE TABLE IF NOT EXISTS `user` (`uid` INTEGER, userid INTEGER, `userphone` TEXT, useremail TEXT, username TEXT, status TEXT, api_token TEXT, PRIMARY KEY(`uid`))")
//        }
//    }

//    abstract fun
}