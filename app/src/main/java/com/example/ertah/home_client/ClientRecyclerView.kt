package com.example.ertah.home_client

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.ertah.DataModels.UserWorkerModel
import com.example.ertah.R
import com.example.ertah.ScheduleActivity

class ClientRecyclerView(
    private val workers:List<UserWorkerModel>,private val clientPhone:String
): RecyclerView.Adapter<ClientRecyclerView.ClientViewHolder>() {

    inner class ClientViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val rating = view.findViewById<TextView>(R.id.rating)
        private val name = view.findViewById<TextView>(R.id.name)
        private val prof = view.findViewById<TextView>(R.id.profession)
        private val root = view.findViewById<ConstraintLayout>(R.id.root)

        fun bind (worker:UserWorkerModel){
            name.text = worker.name
            rating.text = "${worker.rating}/5"
            prof.text = worker.profession
            root.setOnClickListener {
                root.context.startActivity(Intent(root.context,ScheduleActivity::class.java).putExtra("worker_phone",worker.phone).putExtra("client_phone",clientPhone))
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.worker_layout,parent,false)

        return ClientViewHolder(view )
    }

    override fun onBindViewHolder(holder: ClientViewHolder, position: Int) {

        if(holder is ClientViewHolder) holder.bind(workers[position])
    }
    override fun getItemCount(): Int = workers.size
}


