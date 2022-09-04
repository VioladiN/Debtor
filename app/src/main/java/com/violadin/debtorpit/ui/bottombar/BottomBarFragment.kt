package com.violadin.debtorpit.ui.bottombar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.violadin.debtorpit.R
import com.violadin.debtorpit.navigation.NavigationManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.bottom_nav_bar_fragment.view.*
import javax.inject.Inject

@AndroidEntryPoint
class BottomBarFragment : Fragment() {

    @Inject
    lateinit var navController: NavigationManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_nav_bar_fragment, container, false)
        view.bottom_navigation.setupWithNavController(navController.initBottomBarController(this))
        return view
    }

}