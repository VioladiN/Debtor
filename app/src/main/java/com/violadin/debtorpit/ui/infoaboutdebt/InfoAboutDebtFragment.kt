package com.violadin.debtorpit.ui.infoaboutdebt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.violadin.debtorpit.R
import com.violadin.debtorpit.databinding.InfoAboutDebtFragmentBinding

class InfoAboutDebtFragment : Fragment() {

    private lateinit var binding: InfoAboutDebtFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = InfoAboutDebtFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

}