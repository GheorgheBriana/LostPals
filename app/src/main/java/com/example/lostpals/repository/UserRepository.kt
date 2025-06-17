package com.example.lostpals.repository

import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.lostpals.data.dao.UserDao
import com.example.lostpals.data.entity.User

class UserRepository(private val userDao: UserDao) {

    suspend fun registerUser(username: String, email: String, password: String): Long {
        if (userDao.emailExists(email) > 0) {
            throw Exception("Email already registered")
        }
        if (userDao.usernameExists(username) > 0) {
            throw Exception("Username taken")
        }
        if (password.length < 6) {
            throw Exception("Password must be at least 6 characters")
        }

        val passwordHash = BCrypt.withDefaults().hashToString(12, password.toCharArray())

        val user = User(
            id = 0, // Auto-generat de Room
            username = username,
            email = email,
            password = passwordHash
        )
        return userDao.insert(user)
    }

    suspend fun loginUser(username: String, password: String): User {
        val user = userDao.getUserByUsername(username)
        ?: throw Exception("User not found")
        val result = BCrypt.verifyer().verify(password.toCharArray(), user.password)
        if (!result.verified) {
            throw Exception("Invalid credentials")
        }
        return user
    }

}