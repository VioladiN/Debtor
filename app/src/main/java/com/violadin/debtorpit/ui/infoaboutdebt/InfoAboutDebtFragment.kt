package com.violadin.debtorpit.ui.infoaboutdebt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.violadin.debtorpit.R

class InfoAboutDebtFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.info_about_debt_fragment, container, false)
        return view
    }

}