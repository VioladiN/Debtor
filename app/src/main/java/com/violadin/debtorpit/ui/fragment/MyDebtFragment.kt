package com.violadin.debtorpit.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.violadin.debtorpit.R
import com.violadin.debtorpit.domain.model.MyDebtPerson
import com.violadin.debtorpit.domain.model.Person
import com.violadin.debtorpit.presentation.viewmodel.PersonViewModel
import com.violadin.debtorpit.ui.adapter.DebtForMeAdapter
import com.violadin.debtorpit.ui.adapter.MyDebtAdapter
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_header.view.*
import kotlinx.android.synthetic.main.my_debt_fragment.add_person
import kotlinx.android.synthetic.main.my_debt_fragment.list_is_empty_tv
import kotlinx.android.synthetic.main.my_debt_fragment.list_item

class MyDebtFragment : Fragment() {

    private lateinit var viewModel: PersonViewModel
    private val compositeDisposable = CompositeDisposable()

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

        getAllPersonsMyDebt()

        add_person.setOnClickListener {
            val createDebtorFragment = BottomSheetCreateMyDebtPersonFragment(viewModel)
            createDebtorFragment.show(requireActivity().supportFragmentManager, null)
        }

    }

    private fun getAllPersonsMyDebt() {
        compositeDisposable.add(
            viewModel.getAllPersonMyDebt()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { list ->
                    if (list.isEmpty()) {
                        list_is_empty_tv.visibility = View.VISIBLE
                    } else {
                        list_is_empty_tv.visibility = View.GONE
                        list_item.layoutManager = LinearLayoutManager(requireContext())
                        list_item.adapter = MyDebtAdapter(list, requireContext(), viewModel)
                    }
                }
        )
    }

    override fun onPause() {
        compositeDisposable.dispose()
        super.onPause()
    }
}