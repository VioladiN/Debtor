package com.violadin.debtorpit.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.violadin.debtorpit.R
import com.violadin.debtorpit.domain.model.Person
import com.violadin.debtorpit.presentation.viewmodel.PersonViewModel
import com.violadin.debtorpit.ui.adapter.MultiDebtAdapter
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_header.view.*
import kotlinx.android.synthetic.main.multi_debt_fragment.*

class MultiDebtFragment : Fragment() {

    private lateinit var viewModel: PersonViewModel
    private lateinit var persons: List<Person>
    private var idsPersons = arrayListOf<Int>()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.multi_debt_fragment, container, false)
        viewModel = ViewModelProvider(this).get(PersonViewModel::class.java)
        view.header_text.text = getText(R.string.second_page)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getAllPersons()

        add_debts.setOnClickListener {
            if (debt_ed.text.isEmpty()) {
                Toast.makeText(context, R.string.wrong_filed_debt_count, Toast.LENGTH_SHORT).show()
            } else {
                var count = 0
                if (cb_me_too.isChecked)
                    ++count
                count += idsPersons.size
                updatePersons((debt_ed.text.toString().toDouble() / count))
            }
        }
    }

    @SuppressLint("CheckResult")
    private fun updatePersons(debt: Double) {
        Flowable.fromCallable {
            idsPersons.forEach { id ->
                var newDebt = debt
                persons.forEach { person ->
                    if (person.id == id) {
                        newDebt += person.debt!!
                        return@forEach
                    }
                }
                viewModel.updatePerson(id, newDebt)
            }
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe {
            val imm: InputMethodManager? =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.hideSoftInputFromWindow(debt_ed.windowToken, 0)
            debt_ed.text.clear()
            debt_ed.clearFocus()
        }
    }

    @SuppressLint("CheckResult")
    private fun getAllPersons() {
        compositeDisposable.add(
            viewModel.getAllPersons()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { list ->
                    persons = list
                    if (list.isEmpty())
                        list_empty_tv.visibility = View.VISIBLE
                    else {
                        list_empty_tv.visibility = View.GONE
                        list_item.layoutManager = LinearLayoutManager(requireContext())
                        val adapter = MultiDebtAdapter(list, requireContext())
                        adapter.clickEvent.subscribe {
                            idsPersons = it as ArrayList<Int>
                        }
                        list_item.adapter = adapter
                    }
                }
        )
    }

    override fun onPause() {
        compositeDisposable.dispose()
        super.onPause()
    }

}