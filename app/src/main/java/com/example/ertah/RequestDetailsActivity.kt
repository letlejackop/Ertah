package com.example.ertah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.example.ertah.DataModels.RequestsModel
import com.example.ertah.home_worker.WorkerHomeActivity
import com.example.ertah.home_worker.WorkersRecyclerView
import com.example.ertah.login.LoginViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RequestDetailsActivity : AppCompatActivity() {
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_details)

        val id = findViewById<TextView>(R.id.id)
        val description = findViewById<TextView>(R.id.description)
        val time = findViewById<TextView>(R.id.time)
        val date = findViewById<TextView>(R.id.date)
        val state = findViewById<TextView>(R.id.state)
        val client_phone = findViewById<TextView>(R.id.client_phone)
        val worker_phone = findViewById<TextView>(R.id.worker_phone)
        val accept = findViewById<Button>(R.id.accept)
        val reject = findViewById<Button>(R.id.reject)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        val Rid = intent.getStringExtra("id").toString()

        accept.setOnClickListener {
            db.collection(WorkerHomeActivity.COLLECTION_REQUEST).document(Rid)
                .update("state","Accepted")
                .addOnSuccessListener { result ->

                    Log.d("TAG", "listAdapter: ")
                }
                .addOnFailureListener { exception ->
                    Log.w("TAG", "Error getting documents.", exception)
                }
            Toast.makeText(applicationContext,"Request Accepted",
                Toast.LENGTH_LONG).show()
            finish()
        }

        reject.setOnClickListener {
            db.collection(WorkerHomeActivity.COLLECTION_REQUEST).document(Rid)
                .update("state","Rejected")
                .addOnSuccessListener { result ->

                    Log.d("TAG", "listAdapter: ")
                }
                .addOnFailureListener { exception ->
                    Log.w("TAG", "Error getting documents.", exception)
                }
            Toast.makeText(applicationContext,"Request Rejected",
                Toast.LENGTH_LONG).show()
            finish()
        }

        db.collection(WorkerHomeActivity.COLLECTION_REQUEST)
            .get()
            .addOnSuccessListener { result ->
                for (doc in result){
                    if (doc.id ==Rid){
                        val req = doc.toObject(RequestsModel::class.java)
                        id.text = doc.id
                        description.text = req.description
                        time.text = req.time
                        date.text = req.date
                        state.text = req.state
                        client_phone.text = req.client_phone
                        worker_phone.text = req.worker_phone
                    }
                    Log.d("TAG", "listAdapter:${doc.id}")
                }
                Log.d("TAG", "listAdapter: ")
            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents.", exception)
            }
    }
}