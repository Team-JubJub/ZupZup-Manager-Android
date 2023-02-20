package com.example.zupzup_manager.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.zupzup_manager.R
import com.example.zupzup_manager.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initNavController()
        initBottomNavState()
    }

    private fun initBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.findNavController()
        binding.mainBottomNav.setupWithNavController(navController)
    }

    private fun initBottomNavState() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.frag_reservationList -> {
                    showBottomNav()
                }
                R.id.frag_reservationDetail -> {
                    hideBottomNav()
                }
            }
        }
    }

    private fun hideBottomNav() {
        binding.mainBottomNav.visibility = View.GONE
    }

    private fun showBottomNav() {
        binding.mainBottomNav.visibility = View.VISIBLE
    }

}