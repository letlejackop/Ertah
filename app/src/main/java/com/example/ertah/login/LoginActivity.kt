package com.example.ertah.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.example.ertah.DataModels.UserClientModel
import com.example.ertah.R
import com.example.ertah.home_client.ClientHomeActivity
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val phone = findViewById<EditText>(R.id.phone)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.button)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)


        val viewmodel = ViewModelProvider(this)[LoginViewModel::class.java]

        toolbar.setNavigationOnClickListener {
            finish()
        }


        login.setOnClickListener {
            val phoneNumber = phone.text.toString()

            db.collection(COLLECTION_USER_CLIENT).whereEqualTo("phone",phoneNumber)
                .get()
                .addOnSuccessListener { result ->
                    if (!result.isEmpty){
                        val phone = result.documents[0].get("phone").toString()
                        val pass = result.documents[0].get("password").toString()

                        if (password.text.toString() == pass)
                            startActivity(Intent(applicationContext,ClientHomeActivity::class.java).putExtra("phone",phoneNumber))

                    }
                    else{
                        Toast.makeText(applicationContext,"This User does not exist! Please go back to register",
                            Toast.LENGTH_LONG).show()
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("TAG", "Error getting documents.", exception)
                }
        }
    }
    companion object{
        const val COLLECTION_USER_CLIENT = "user_client"
        const val COLLECTION_USER_WORKER = "user_worker"
        const val COLLECTION_REQUEST = "request"
    }
}