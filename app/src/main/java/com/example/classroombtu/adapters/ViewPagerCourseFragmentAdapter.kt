package com.example.classroombtu.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.classroombtu.fragments.courseinfofragments.GroupsFragment
import com.example.classroombtu.fragments.courseinfofragments.MaterialsFragment
import com.example.classroombtu.fragments.courseinfofragments.RateFragment
import com.example.classroombtu.fragments.courseinfofragments.SyllabusFragment

class ViewPagerCourseFragmentAdapter(activity: FragmentActivity): FragmentStateAdapter(activity) {

    override fun getItemCount() = 4

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> SyllabusFragment()
            1 -> GroupsFragment()
            2 -> RateFragment()
            3 -> MaterialsFragment()
            else -> SyllabusFragment()
        }

    }

}