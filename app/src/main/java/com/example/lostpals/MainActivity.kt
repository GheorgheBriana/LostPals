package com.example.lostpals

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.lostpals.ui.auth.LoginActivity
import com.example.lostpals.ui.auth.RegisterActivity
import com.example.lostpals.util.SessionManager
import com.example.lostpals.viewmodel.AuthViewModel
import kotlin.jvm.java

class MainActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionManager = SessionManager(this)

        if (!sessionManager.isLoggedIn()) {
            redirectToLogin()
            return
        }

        val userId = sessionManager.getUserId()
        Toast.makeText(this, "User ID: $userId", Toast.LENGTH_SHORT).show()
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
// LOGOUT
        findViewById<Button>(R.id.btnLogout).setOnClickListener {
            ViewModelProvider(this).get(AuthViewModel::class.java).logout()
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
            redirectToLogin()
        }


        // UNCOMMENT WHEN CREATEPOST & ACCOUNT ARE READY
//// ACCESS CREATE POST FROM MENU
//        findViewById<ImageView>(R.id.createPostIcon).setOnClickListener {
//            startActivity(Intent(this, CreatePostFragment::class.java))
//        }
//// ACCESS ACCOUNT FROM MENU
//        findViewById<ImageView>(R.id.accountIcon).setOnClickListener {
//            startActivity(Intent(this, AccountFragment::class.java))
//        }
// ACCESS HOMEPAGE FROM ICON
        findViewById<ImageView>(R.id.logo).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun redirectToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}