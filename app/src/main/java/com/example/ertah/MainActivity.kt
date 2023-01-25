package com.example.ertah

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.ertah.Register.RegisterActivity
import com.example.ertah.Register.RegisterWorkerActivity
import com.example.ertah.login.LoginActivity
import com.example.ertah.login.LoginWorkerActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val login = findViewById<Button>(R.id.login)
        val workerLogin = findViewById<Button>(R.id.login_worker)
        val register = findViewById<Button>(R.id.register)
        val registerWorker = findViewById<Button>(R.id.register_worker)

        login.setOnClickListener {
            startActivity(Intent(applicationContext,LoginActivity::class.java))
        }
        workerLogin.setOnClickListener {
            startActivity(Intent(applicationContext,LoginWorkerActivity::class.java))
        }

        register.setOnClickListener {
            startActivity(Intent(applicationContext,RegisterActivity::class.java))
        }

        registerWorker.setOnClickListener {
            startActivity(Intent(applicationContext,RegisterWorkerActivity::class.java))
        }

    }
}