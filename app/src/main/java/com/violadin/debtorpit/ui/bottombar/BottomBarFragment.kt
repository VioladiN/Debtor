package com.violadin.debtorpit.ui.bottombar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.ui.setupWithNavController
import com.violadin.debtorpit.databinding.BottomNavBarFragmentBinding
import com.violadin.debtorpit.navigation.NavigationManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BottomBarFragment : Fragment() {

    @Inject
    lateinit var navController: NavigationManager
    private lateinit var binding: BottomNavBarFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomNavBarFragmentBinding.inflate(inflater, container, false)
        binding.bottomNavigation.setupWithNavController(navController.initBottomBarController(this))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}