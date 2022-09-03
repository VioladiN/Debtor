package com.violadin.debtorpit.ui.bottombar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.violadin.debtorpit.R
import kotlinx.android.synthetic.main.bottom_nav_bar_fragment.view.*

class BottomBarFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_nav_bar_fragment, container, false)
        val navHostFragment = childFragmentManager.findFragmentById(R.id.bottom_bar_container) as NavHostFragment
        val navController = navHostFragment.navController.apply {
            graph = navInflater.inflate(R.navigation.bottom_bar_nav_graph)
        }
        view.bottom_navigation.setupWithNavController(navController)
        return view
    }

}