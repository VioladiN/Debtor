package com.violadin.debtorpit.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.violadin.debtorpit.R
import com.violadin.debtorpit.domain.model.Person
import com.violadin.debtorpit.presentation.viewmodel.PersonViewModel
import kotlinx.android.synthetic.main.fragment_header.view.*

class MyDebtFragment : Fragment() {

    private lateinit var viewModel: PersonViewModel
    private lateinit var persons: List<Person>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.my_debt_fragment, container, false)
        viewModel = ViewModelProvider(this).get(PersonViewModel::class.java)
        view.header_text.text = R.string.third_page.toString()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}