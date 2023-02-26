package com.violadin.debtorpit.navigation

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.violadin.debtorpit.R

class NavigationManager {

    var mainActivity: Activity? = null
    var bottomBarFragment: Fragment? = null

    var mainActivityController: NavController? = null
    var bottomBarController: NavController? = null
    var activeMavController: NavController? = null

    fun initActivityController(activity: Activity) {
        mainActivity = activity
        mainActivityController = mainActivity?.let {
            it.findNavController(R.id.activity_container).apply {
                graph = navInflater.inflate(R.navigation.activity_nav_graph)
            }
        }
    }

    fun initBottomBarController(fragment: Fragment): NavController {
        bottomBarFragment = fragment
        val navHostFragment =
            fragment.childFragmentManager.findFragmentById(R.id.bottom_bar_container) as NavHostFragment
        val navController = navHostFragment.navController.apply {
            graph = navInflater.inflate(R.navigation.bottom_bar_nav_graph)
        }
        bottomBarController = navController
        return navController
    }
}