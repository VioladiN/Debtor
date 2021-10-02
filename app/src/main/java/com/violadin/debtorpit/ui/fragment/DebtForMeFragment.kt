package com.violadin.debtorpit.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.violadin.debtorpit.R
import com.violadin.debtorpit.domain.model.Person
import com.violadin.debtorpit.presentation.viewmodel.PersonViewModel
import com.violadin.debtorpit.ui.adapter.DebtForMeAdapter
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.bottom_sheet_create_debtor_fragment.*
import kotlinx.android.synthetic.main.debt_for_me_fragment.*
import kotlinx.android.synthetic.main.fragment_header.view.*
import kotlinx.android.synthetic.main.my_debt_fragment.view.*

class DebtForMeFragment: Fragment() {

    private lateinit var viewModel: PersonViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.debt_for_me_fragment, container, false)
        viewModel = ViewModelProvider(this).get(PersonViewModel::class.java)
        view.header_text.text = R.string.first_page.toString()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getAllPersons()

        add_person.setOnClickListener {
            val createDebtorFragment = BottomSheetCreateDebtorFragment(viewModel)
            createDebtorFragment.show(requireActivity().supportFragmentManager, null)
        }
    }

    @SuppressLint("CheckResult")
    private fun getAllPersons() {
        viewModel.getAllPersons()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list ->
                if (list.isEmpty())
                    list_is_empty_tv.visibility = View.VISIBLE
                else {
                    list_is_empty_tv.visibility = View.GONE
                    list_item.layoutManager = LinearLayoutManager(requireContext())
                    list_item.adapter = DebtForMeAdapter(list, requireContext())
                }
            }
    }

    @SuppressLint("CheckResult")
    private fun addPerson(person: Person) {
        Flowable.fromCallable {
            viewModel.addPerson(person)
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe {
            Toast.makeText(context, R.string.person_added, Toast.LENGTH_SHORT).show()
        }
    }
}