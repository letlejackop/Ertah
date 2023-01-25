package com.example.ertah.home_client

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.ertah.ClientRequestsActivity
import com.example.ertah.DataModels.UserWorkerModel
import com.example.ertah.R
import com.example.ertah.login.LoginActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class ClientHomeActivity : AppCompatActivity() {
    private val db = Firebase.firestore
    private lateinit var adapter :ClientRecyclerView
    private lateinit var WorkersList: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_home)
        WorkersList = findViewById(R.id.list)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        val phoneNumber = intent.getStringExtra("phone").toString()

        toolbar.setNavigationOnClickListener {
            finish()
        }
        toolbar.setOnMenuItemClickListener {
            startActivity(Intent(applicationContext,ClientRequestsActivity::class.java).putExtra("phone",phoneNumber))
            true
        }

        listAdapter()


    }
    private fun listAdapter(){
        val workers = arrayListOf<UserWorkerModel>()
        val phoneNumber = intent.getStringExtra("phone").toString()

        db.collection(COLLECTION_USER_WORKER)
            .get()
            .addOnSuccessListener { result ->
                for (doc in result){
                    val worker = doc.toObject(UserWorkerModel::class.java)
                    workers.add(worker)
                    Log.d("TAG", "listAdapter: $worker")
                }
                Log.d("TAG", "listAdapter: $workers")
                adapter = ClientRecyclerView(workers,phoneNumber)
                WorkersList.adapter = adapter
            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents.", exception)
            }
    }

    override fun onResume() {
        super.onResume()
        listAdapter()
    }
    companion object{
        const val COLLECTION_USER_CLIENT = "user_client"
        const val COLLECTION_USER_WORKER = "user_worker"
        const val COLLECTION_REQUEST = "request"
    }
}

