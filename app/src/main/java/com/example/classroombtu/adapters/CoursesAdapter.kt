package com.example.classroombtu.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.classroombtu.R
import com.example.classroombtu.models.Subject
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CoursesAdapter (val context: Context, private val subjectList: ArrayList<Subject>, private val listener: OnItemClickListener): RecyclerView.Adapter<CoursesAdapter.ViewHolder>() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance().getReference("Students")
    private val currentStudent = db.child(auth.currentUser!!.uid)

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {

        val lecturer: TextView = view.findViewById(R.id.lecturer)
        val courseName: TextView = view.findViewById(R.id.courseName)
        val courseImage: ImageView = view.findViewById(R.id.courseImage)

        private val item: RelativeLayout = itemView.findViewById(R.id.courseItem)

        init {
            item.setOnClickListener(this)
        }

        override fun onClick(p0: View?) { // new
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_courses, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("RecyclerView")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        currentStudent.child("subjects").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val currentSubject = subjectList[position]

                holder.lecturer.text = currentSubject.lecturer
                holder.courseName.text = currentSubject.name

                Glide.with(context)
                    .load(currentSubject.imageUrl)
                    .into(holder.courseImage)

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    override fun getItemCount() = subjectList.size

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

}