package com.example.ertah.home_worker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.ertah.DataModels.RequestsModel
import com.example.ertah.DataModels.UserWorkerModel
import com.example.ertah.R
import com.example.ertah.home_client.ClientRecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class WorkerHomeActivity : AppCompatActivity() {
    private val db = Firebase.firestore
    private lateinit var adapter :WorkersRecyclerView
    private lateinit var RequestsList: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_worker_home)
        RequestsList = findViewById(R.id.list)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        toolbar.setNavigationOnClickListener {
            finish()
        }
        listAdapter()
    }
    private fun listAdapter(){
        val reqs = arrayListOf<RequestsModel>()
        val id = arrayListOf<String>()
        val phoneNumber = intent.getStringExtra("phone").toString()


        db.collection(COLLECTION_REQUEST).whereEqualTo("worker_phone",phoneNumber)
            .get()
            .addOnSuccessListener { result ->
                for (doc in result){
                    val req = doc.toObject(RequestsModel::class.java)
                    id.add(doc.id)
                    reqs.add(req)
                    Log.d("TAG", "listAdapter: $req")
                }
                Log.d("TAG", "listAdapter: $reqs")
                adapter = WorkersRecyclerView(reqs,id)
                RequestsList.adapter = adapter
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
