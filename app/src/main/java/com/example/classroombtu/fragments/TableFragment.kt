package com.example.classroombtu.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.classroombtu.R
import com.example.classroombtu.databinding.BottomSheetCourseInfoBinding
import com.example.classroombtu.databinding.FragmentTableBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TableFragment: Fragment (R.layout.fragment_table) {

    private var _binding: FragmentTableBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTableBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}