package com.martins.milton.zipcode.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.martins.milton.zipcode.data.models.Person

@Database(entities = [Person::class], version = 1)
abstract class ZipcodeDatabase : RoomDatabase() {

    abstract fun peopleDao(): PeopleDao

    companion object {

        private var INSTANCE: ZipcodeDatabase? = null

        private val lock = Any()

        fun getInstance(context: Context): ZipcodeDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ZipcodeDatabase::class.java, "Zipcode.db"
                    )
                        .build()
                }
                return INSTANCE!!
            }
        }
    }

}