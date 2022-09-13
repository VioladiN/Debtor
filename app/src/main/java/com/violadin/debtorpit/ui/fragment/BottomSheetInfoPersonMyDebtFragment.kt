package com.violadin.debtorpit.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.violadin.debtorpit.R
import com.violadin.debtorpit.database.tables.Person
import kotlinx.android.synthetic.main.bottom_sheet_header_add_person.button_cancel
import kotlinx.android.synthetic.main.bottom_sheet_header_info_person.*
import kotlinx.android.synthetic.main.bottom_sheet_info_person_fragment.*

class BottomSheetInfoPersonMyDebtFragment() : BottomSheetDialogFragment() {

    private var person: Person? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_info_person_fragment, container, false)
        if (arguments != null) {
            person = arguments?.get("person") as Person?
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edit_debt_button.visibility = View.GONE
        call_person.visibility = View.GONE
        phone_text.visibility=View.GONE

//        first_name_text.text = person!!.firstName
//        if (person?.lastName.isNullOrEmpty())
//            last_name_text.visibility = View.GONE
//        else
//            last_name_text.text = person!!.lastName
//        debt_count.text = person!!.debt.toString()

        drop_debt_button.setOnClickListener {
            it.apply { isEnabled = false; postDelayed({isEnabled = true}, 1000) }
//            updateDebtOfPerson(0.0)
        }

        delete_person_button.setOnClickListener {
            it.apply { isEnabled = false; postDelayed({isEnabled = true}, 1000) }
//            showDeleteDialog()
        }

        button_cancel.setOnClickListener {
            it.apply { isEnabled = false; postDelayed({isEnabled = true}, 1000) }
            dismiss()
        }
    }

//    @SuppressLint("CheckResult")
//    private fun updateDebtOfPerson(debt: Double) {
//        Flowable.fromCallable {
//            viewModel.updatePersonMyDebt(person!!.id!!, debt)
//        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe {
//            debt_count.text = debt.toString()
//            person!!.debt = debt
//        }
//    }

//    @SuppressLint("CheckResult")
//    private fun showDeleteDialog() {
//        val dialog = AlertDialog.Builder(context)
//            .setMessage(R.string.deleting_message)
//            .setPositiveButton(R.string.delete) { dialog, which ->
//                Flowable.fromCallable {
//                    viewModel.deletePersonMyDebt(person!!)
//                }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe {
//                    dismiss()
//                }
//                dialog.cancel()
//            }
//            .setNegativeButton(R.string.cancel) { dialog, which ->
//                dialog.cancel()
//            }
//            .show()
//        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(requireContext().getColor(R.color.red_base))
//    }

    override fun getTheme(): Int =
        R.style.AppBottomSheetDialogTheme

}