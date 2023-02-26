package com.violadin.debtorpit.ui.bottombar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUiSaveStateControl
import com.violadin.debtorpit.R
import com.violadin.debtorpit.databinding.BottomNavBarFragmentBinding
import com.violadin.debtorpit.navigation.NavigationManager
import com.violadin.debtorpit.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BottomBarFragment : Fragment() {

    @Inject
    lateinit var navController: NavigationManager
    private lateinit var binding: BottomNavBarFragmentBinding

    @NavigationUiSaveStateControl
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomNavBarFragmentBinding.inflate(inflater, container, false)
        NavigationUI.setupWithNavController(
            binding.bottomNavigation,
            navController.initBottomBarController(this),
            false
        )
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        val fragmentContainer = childFragmentManager.findFragmentById(R.id.bottom_bar_container)
        fragmentContainer?.childFragmentManager?.addOnBackStackChangedListener {
            (activity as MainActivity).backButtonVisibility(
                fragmentContainer.childFragmentManager.backStackEntryCount,
                navController.bottomBarController
            )
        }
    }
}