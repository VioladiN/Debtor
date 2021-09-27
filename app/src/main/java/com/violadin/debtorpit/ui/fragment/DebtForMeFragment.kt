package com.violadin.debtorpit.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.violadin.debtorpit.presentation.viewmodel.PersonViewModel

class DebtForMeFragment: Fragment() {

    private lateinit var viewModel: PersonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(PersonViewModel::class.java)

    }

    override fun onResume() {
        super.onResume()

    }

}