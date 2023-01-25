package com.example.ertah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.example.ertah.DataModels.RequestsModel
import com.example.ertah.home_worker.WorkerHomeActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ClientRequestDetailsActivity : AppCompatActivity() {
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_request_details)
        val id = findViewById<TextView>(R.id.id)
        val description = findViewById<TextView>(R.id.description)
        val time = findViewById<TextView>(R.id.time)
        val date = findViewById<TextView>(R.id.date)
        val state = findViewById<TextView>(R.id.state)
        val client_phone = findViewById<TextView>(R.id.client_phone)
        val worker_phone = findViewById<TextView>(R.id.worker_phone)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        val Rid = intent.getStringExtra("id").toString()

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