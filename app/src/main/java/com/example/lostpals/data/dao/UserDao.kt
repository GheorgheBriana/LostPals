package com.example.lostpals.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.lostpals.data.entity.User

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User): Long
    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): User?
    @Query("SELECT * FROM users WHERE email = :email AND password = :passwordHash LIMIT 1")
    suspend fun loginUser(email: String, passwordHash: String): User?
    @Query("SELECT COUNT(*) FROM users WHERE email = :email")
    suspend fun emailExists(email: String): Int
    @Query("SELECT COUNT(*) FROM users WHERE username = :username")
    suspend fun usernameExists(username: String): Int
  }