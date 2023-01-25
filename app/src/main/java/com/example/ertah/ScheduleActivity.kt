package com.example.ertah

import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.example.ertah.DataModels.UserWorkerModel
import com.example.ertah.home_client.ClientHomeActivity
import com.example.ertah.home_client.ClientRecyclerView
import com.example.ertah.login.LoginViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ScheduleActivity : AppCompatActivity() {
    private val db = Firebase.firestore
    private  var date:String = ""
    private  var time:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)
        val desc = findViewById<EditText>(R.id.description)
        val submit = findViewById<Button>(R.id.button)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)


        val cPhone = intent.getStringExtra("client_phone")
        val wPhone = intent.getStringExtra("worker_phone")

        toolbar.setNavigationOnClickListener {
            finish()
        }

        OnClickTime()
        OnClickDate()
        submit.setOnClickListener {

            val Request = hashMapOf(
                "description" to desc.text.toString(),
                "time" to time,
                "date" to date,
                "iscomplete" to 0,
                "state" to "waiting for response",
                "client_phone" to cPhone,
                "worker_phone" to wPhone,
                "rating" to 0.0
            )

            db.collection(COLLECTION_REQUEST)
                .add(Request)
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(applicationContext,"Request Added",
                        Toast.LENGTH_LONG).show()
                    finish()
                    Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { exception ->
                    Log.w("TAG", "Error getting documents.", exception)
                }
        }
    }

    private fun OnClickDate(){
        val datePicker = findViewById<DatePicker>(R.id.datePicker)
        val today = Calendar.getInstance()
        datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        ) { view, year, month, day ->
            val month = month + 1
            val msg = "$day/$month/$year"
            date = msg
        }
    }
    private fun OnClickTime() {
        val timePicker = findViewById<TimePicker>(R.id.timePicker)
        timePicker.setOnTimeChangedListener { _, hour, minute -> var hour = hour
            var am_pm = ""
            // AM_PM decider logic
            when {hour == 0 -> { hour += 12
                am_pm = "AM"
            }
                hour == 12 -> am_pm = "PM"
                hour > 12 -> { hour -= 12
                    am_pm = "PM"
                }
                else -> am_pm = "AM"
            }
                val h = if (hour < 10) "0" + hour else hour
                val min = if (minute < 10) "0" + minute else minute
                // display format of time
                val msg = "$h:$min $am_pm"
            time = msg
        }
    }
    companion object{
        const val COLLECTION_REQUEST = "request"
    }
}