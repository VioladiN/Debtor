package com.violadin.debtorpit.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.violadin.debtorpit.R
import kotlinx.android.synthetic.main.fragment_header.view.*

class MyDebtFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?):
            View? {
        val view = inflater.inflate(R.layout.my_debt_fragment, container, false)
        view.header_text.text = getString(R.string.third_page)
        return view
    }

    override fun onResume() {
        super.onResume()
    }
}