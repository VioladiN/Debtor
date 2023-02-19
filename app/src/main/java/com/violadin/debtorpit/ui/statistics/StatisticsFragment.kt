package com.violadin.debtorpit.ui.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.violadin.debtorpit.R
import com.violadin.debtorpit.databinding.StatisticsFragmentBinding
import com.violadin.debtorpit.navigation.NavigationManager
import com.violadin.debtorpit.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StatisticsFragment : Fragment() {

    @Inject
    lateinit var navigationManager: NavigationManager
    private lateinit var binding: StatisticsFragmentBinding
    private val viewModel: StatisticsFragmentVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = StatisticsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).changeHeader(R.string.statistics)
    }
}