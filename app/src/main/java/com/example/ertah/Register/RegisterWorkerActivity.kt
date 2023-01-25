package com.example.ertah.Register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.ertah.DataModels.UserClientModel
import com.example.ertah.DataModels.UserWorkerModel
import com.example.ertah.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterWorkerActivity : AppCompatActivity() {
    private val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_worker)

        val phone = findViewById<TextView>(R.id.phone)
        val name = findViewById<EditText>(R.id.name)
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val register = findViewById<Button>(R.id.button)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val elect = findViewById<RadioButton>(R.id.electrician)
        val plumber = findViewById<RadioButton>(R.id.plumber)



        toolbar.setNavigationOnClickListener {
            finish()
        }

        register.setOnClickListener {
            val phoneNumber = phone.text.toString()
            var proff = ""
           if( elect.isChecked)
               proff =elect.text.toString()
            if (plumber.isChecked)
                proff =plumber.text.toString()

            db.collection(COLLECTION_USER_WORKER).whereEqualTo("phone",phoneNumber)
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
                            "password" to password.text.toString(),
                            "profession" to proff,
                            "rating" to 0.0
                        )
                        db.collection(COLLECTION_USER_WORKER)
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
        const val COLLECTION_USER_WORKER = "user_worker"
    }
}