package com.example.oskartestapp.data.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.oskartestapp.domain.model.AuthItem
import kotlinx.coroutines.flow.Flow

@Dao
interface AuthDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAuth(authItem: AuthItem)

    @Query("SELECT * FROM AuthItem WHERE id=:key")
    suspend fun getAuth(key: String): AuthItem

    @Query("SELECT * FROM AuthItem WHERE id=:key")
    fun getAuthFlow(key: String): Flow<AuthItem>

}