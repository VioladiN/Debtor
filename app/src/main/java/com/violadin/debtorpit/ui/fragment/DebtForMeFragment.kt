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
import androidx.recyclerview.widget.RecyclerView
import com.violadin.debtorpit.R
import com.violadin.debtorpit.domain.model.Person
import com.violadin.debtorpit.presentation.viewmodel.PersonViewModel
import com.violadin.debtorpit.ui.adapter.DebtForMeAdapter
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.bottom_sheet_create_debtor_fragment.*
import kotlinx.android.synthetic.main.debt_for_me_fragment.*
import kotlinx.android.synthetic.main.fragment_header.view.*
import kotlinx.android.synthetic.main.my_debt_fragment.view.*

class DebtForMeFragment: Fragment() {

    private lateinit var viewModel: PersonViewModel
    private val compositeDisposable = CompositeDisposable()

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

        list_item.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 || dy < 0 && add_person.isShown)
                    add_person.hide()
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                    add_person.show()

                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }

    @SuppressLint("CheckResult")
    private fun getAllPersons() {
        compositeDisposable.add(
            viewModel.getAllPersons()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { list ->
                    if (list.isEmpty())
                        list_is_empty_tv.visibility = View.VISIBLE
                    else {
                        list_is_empty_tv.visibility = View.GONE
                        list_item.layoutManager = LinearLayoutManager(requireContext())
                        list_item.adapter = DebtForMeAdapter(list, requireContext(), viewModel)
                    }
                }
        )
    }

    override fun onPause() {
        compositeDisposable.dispose()
        super.onPause()
    }

}