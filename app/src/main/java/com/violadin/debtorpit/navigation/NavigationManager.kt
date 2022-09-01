package com.violadin.debtorpit.navigation

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.violadin.debtorpit.R

class NavigationManager {

    private var mainActivity: Activity? = null
    var bottomBarFragment: Fragment? = null

    var mainActivityController: NavController? = null

    fun initActivityController(activity: Activity) {
        mainActivity = activity
        mainActivityController = mainActivity?.let {
            it.findNavController(R.id.activity_container).apply {
                graph = navInflater.inflate(R.navigation.activity_nav_graph)
            }
        }
    }
}