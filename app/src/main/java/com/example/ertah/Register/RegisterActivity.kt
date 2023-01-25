package com.example.ertah.Register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.ertah.DataModels.UserClientModel
import com.example.ertah.DataModels.UserWorkerModel
import com.example.ertah.R
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val phone = findViewById<TextView>(R.id.phone)
        val name = findViewById<EditText>(R.id.name)
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val register = findViewById<Button>(R.id.button)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)


        toolbar.setNavigationOnClickListener {
            finish()
        }

        register.setOnClickListener {
            val phoneNumber = phone.text.toString()

            db.collection(COLLECTION_USER_CLIENT).whereEqualTo("phone",phoneNumber)
                    .get()
                    .addOnSuccessListener { result ->


                        if (!result.isEmpty)
                            Toast.makeText(
                                applicationContext,
                                "A user with phone Number $phoneNumber already exists Please go back to Login page",
                                Toast.LENGTH_LONG
                            ).show()
                        else {
                            val user = hashMapOf(
                                "name" to name.text.toString(),
                                "email" to email.text.toString(),
                                "phone" to phone.text.toString(),
                                "password" to password.text.toString()
                            )
                            db.collection(COLLECTION_USER_CLIENT)
                                .add(user)
                                .addOnSuccessListener { documentReference ->
                                    Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
                                }
                                .addOnFailureListener { e ->
                                    Log.w("TAG", "Error adding document", e)
                                }
                            Toast.makeText(applicationContext, "Registered successfully", Toast.LENGTH_SHORT).show()
                        }
            }.addOnFailureListener { exception ->
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
