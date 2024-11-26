package com.example.damn_jmhg_examenll.room.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.damn_jmhg_examenll.room.entities.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): Flow<List<User>>

    @Query("SELECT * FROM user WHERE id = :id")
    fun getById(id: Long): Flow<User>

    @Insert
    suspend fun add(user: User): Long

    @Delete
    suspend fun delete(user: User)
}