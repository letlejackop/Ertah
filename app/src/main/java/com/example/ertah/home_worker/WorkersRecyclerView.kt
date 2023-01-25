package com.example.ertah.home_worker

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.ertah.DataModels.RequestsModel
import com.example.ertah.DataModels.UserWorkerModel
import com.example.ertah.R
import com.example.ertah.RequestDetailsActivity
import com.example.ertah.ScheduleActivity

class WorkersRecyclerView(
    private val requests:List<RequestsModel>, private val  id:List<String>
): RecyclerView.Adapter<WorkersRecyclerView.WorkerViewHolder>() {

    inner class WorkerViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val state = view.findViewById<TextView>(R.id.state)
        private val desc = view.findViewById<TextView>(R.id.description)
        private val root = view.findViewById<ConstraintLayout>(R.id.root)

        fun bind (request: RequestsModel,Rid:String){
            state.text = request.state

            desc.text = request.description
            root.setOnClickListener {
                root.context.startActivity(Intent(root.context, RequestDetailsActivity::class.java).putExtra("id",Rid))
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkerViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.request_layout,parent,false)

        return WorkerViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorkerViewHolder, position: Int) {

        if(holder is WorkerViewHolder) holder.bind(requests[position],id[position])
    }
    override fun getItemCount(): Int = requests.size
}
