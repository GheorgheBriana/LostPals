package com.example.lostpals.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("LostPalsPrefs", Context.MODE_PRIVATE)
    companion object {
        const val KEY_USER_ID = "user_id"
        const val IS_LOGGED_IN = "is_logged_in"
    }
    fun saveUserId(userId: Long) {
        prefs.edit { putLong(KEY_USER_ID, userId) }
    }
    fun getUserId(): Long {
        return prefs.getLong(KEY_USER_ID, -1) // -1 adica niciun user
    }
    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(IS_LOGGED_IN, false)
    }
    fun setLoggedIn(isLoggedIn: Boolean) {
        prefs.edit { putBoolean(IS_LOGGED_IN, isLoggedIn) }
    }
    fun logout() {
        prefs.edit { clear() }
    }
}