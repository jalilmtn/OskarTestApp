package com.example.oskartestapp.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.oskartestapp.domain.model.AuthItem


@Database(
    entities = [AuthItem::class],
    version = 1
)
abstract class MyDB : RoomDatabase() {
    abstract val authDao: AuthDao

    companion object {
        const val DB_NAME = "my_db"
    }

}