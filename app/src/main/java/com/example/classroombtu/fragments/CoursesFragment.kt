package com.example.classroombtu.fragments

import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.classroombtu.R
import com.example.classroombtu.adapters.CoursesAdapter
import com.example.classroombtu.databinding.FragmentCoursesBinding
import com.example.classroombtu.models.Subject
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import android.os.Bundle as Bundle

class CoursesFragment: Fragment (R.layout.fragment_courses), CoursesAdapter.OnItemClickListener {

    private var _binding: FragmentCoursesBinding? = null
    private val binding get() = _binding!!

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private lateinit var subjectRecyclerView: RecyclerView
    private lateinit var subjectArrayList: ArrayList<Subject>
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance().getReference("Students")
    private val currentStudent = db.child(auth.currentUser!!.uid)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoursesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subjectRecyclerView = view.findViewById(R.id.subjectListRecycler)
        subjectRecyclerView.layoutManager = LinearLayoutManager(activity)
        subjectRecyclerView.setHasFixedSize(true)

        subjectArrayList = arrayListOf()
        getSubjectData()

        swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            getSubjectData()
        }

    }

    private fun getSubjectData() {

        currentStudent.child("subjects").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {

                    subjectArrayList.clear()

                    if (swipeRefreshLayout.isRefreshing) {
                        Handler().postDelayed({
                            swipeRefreshLayout.isRefreshing = false
                        }, 500)
                    }

                    for (subjectSnapshot in snapshot.children) {
                        val subject = subjectSnapshot.getValue(Subject::class.java)?: return
                        subjectArrayList.add(subject)
                    }

                    binding.itemLoading.visibility = View.GONE
                    subjectRecyclerView.adapter = CoursesAdapter(requireContext(), subjectArrayList, this@CoursesFragment)

                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

    override fun onItemClick(position: Int) {

        val currentSubject = subjectArrayList[position]

        val courseName = currentSubject.name.toString()
        val courseCode = currentSubject.code.toString()
        val courseCredit = currentSubject.credit.toString()
        val courseMinCredit = currentSubject.minCredit.toString()
        val coursePoint = currentSubject.point.toString()

        val modalBottomSheet = CourseInfoBottomSheet()

        val bundle = Bundle()

//        bundle.putString("courseName", courseName)
//        bundle.putString("courseCode", courseCode)
//        bundle.putString("courseCredit", courseCredit)
//        bundle.putString("courseMinCredit", courseMinCredit)
//        bundle.putString("coursePoint", coursePoint)
        bundle.putInt("courseId", position)

        modalBottomSheet.arguments = bundle

        modalBottomSheet.show(parentFragmentManager, CourseInfoBottomSheet.TAG)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}