package com.example.classroombtu.fragments

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.classroombtu.R
import com.example.classroombtu.adapters.ViewPagerCourseFragmentAdapter
import com.example.classroombtu.databinding.BottomSheetCourseInfoBinding
import com.example.classroombtu.models.Subject
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CourseInfoBottomSheet: BottomSheetDialogFragment() {

    private var _binding: BottomSheetCourseInfoBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewPagerCourseFragmentAdapter: ViewPagerCourseFragmentAdapter
    private var currentSubjectId: Int = 0
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance().getReference("Students")

    companion object {
        const val TAG = "CourseInfoBottomSheet"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetCourseInfoBinding.inflate(inflater, container, false)

//        binding.courseName.text = arguments?.getString("courseName")
//        binding.courseCode.text = arguments?.getString("courseCode")
//        binding.courseMinCredit.text = arguments?.getString("courseMinCredit")
//        binding.courseCredit.text = arguments?.getString("courseCredit")
//        binding.coursePoint.text = arguments?.getString("coursePoint")

        currentSubjectId = arguments?.getInt("courseId")!!.toInt()
        getCurrentSubject(currentSubjectId)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* View Pager Course Fragment Adapter */

        viewPagerCourseFragmentAdapter = ViewPagerCourseFragmentAdapter(requireActivity())
        binding.viewPager.adapter = viewPagerCourseFragmentAdapter

        TabLayoutMediator(binding.viewPagerTabs, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.setIcon(R.drawable.ic_baseline_assignment_24)
                1 -> tab.setIcon(R.drawable.ic_baseline_groups_24)
                2 -> tab.setIcon(R.drawable.ic_baseline_star_24)
                3 -> tab.setIcon(R.drawable.ic_baseline_book_24)
            }
        }.attach()

        /*  */

    }

    private fun getCurrentSubject(currentSubjectId: Int) {

        db.child(auth.currentUser!!.uid).child("subjects").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val currentSubject = snapshot.child(currentSubjectId.toString()).getValue(Subject::class.java)?: return
                binding.courseName.text = currentSubject.name
                binding.courseCode.text = currentSubject.code
                binding.courseMinCredit.text = currentSubject.minCredit.toString()
                binding.courseCredit.text = currentSubject.credit.toString()
                binding.coursePoint.text = currentSubject.point.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return (super.onCreateDialog(savedInstanceState) as BottomSheetDialog).apply {
            behavior.setPeekHeight(resources.getDimension(R.dimen.bottom_sheet_height).toInt())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}