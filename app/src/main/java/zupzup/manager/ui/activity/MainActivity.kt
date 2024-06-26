package zupzup.manager.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import zupzup.manager.R
import zupzup.manager.databinding.ActivityMainBinding
import zupzup.manager.ui.item.ItemFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ItemFragment.NavigationBarVisibilityListener {

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

    override fun setNavigationBarVisibility(isVisible: Boolean) {
        binding.mainBottomNav.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun initBottomNavState() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.frag_orderList -> {
                    showBottomNav()
                }

                R.id.frag_orderDetail -> {
                    hideBottomNav()
                }

                R.id.frag_item -> {
                    showBottomNav()
                }

                R.id.frag_item_detail -> {
                    hideBottomNav()
                }

                R.id.frag_setting -> {
                    showBottomNav()
                }

                R.id.frag_store -> {
                    hideBottomNav()
                }

                R.id.frag_leave -> {
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