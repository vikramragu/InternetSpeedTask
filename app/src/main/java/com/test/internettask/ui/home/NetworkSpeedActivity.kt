package com.test.internettask.ui.home

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.test.internettask.R
import com.test.internettask.databinding.ActivityMainBinding
import com.test.internettask.ui.base.BaseActivity

class NetworkSpeedActivity : BaseActivity<ActivityMainBinding>() {

    private lateinit var navController: NavController

    override val binding: Int
        get() = R.layout.activity_main

    override fun created() {
        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_fragment) as NavHostFragment

        navController = navHostFragment.findNavController()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onNavigateUp()
    }

}