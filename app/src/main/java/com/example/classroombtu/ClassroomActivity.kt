package com.example.classroombtu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.classroombtu.databinding.ActivityClassroomBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class ClassroomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClassroomBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityClassroomBinding.inflate(layoutInflater)
        supportActionBar?.hide() // to hide action bar
        setContentView(binding.root)

        binding.avatarTextView.text = auth.currentUser?.email.toString().first().uppercase()

        /* burger button function */

        binding.burgerButton.setOnClickListener {
            binding.layoutDrawer.openDrawer(GravityCompat.START)
        }

        navigationFunction()

    }

    private fun navigationFunction() {

        val burgerNavigationView = findViewById<NavigationView>(R.id.sidebarNavigation)
        val controller = findNavController(R.id.nav_host_fragment)

        val appBarConfig = AppBarConfiguration(setOf(
            R.id.coursesFragment,
            R.id.tableFragment
        ))

        setupActionBarWithNavController(controller, appBarConfig)
        burgerNavigationView.setupWithNavController(controller)

    }

}