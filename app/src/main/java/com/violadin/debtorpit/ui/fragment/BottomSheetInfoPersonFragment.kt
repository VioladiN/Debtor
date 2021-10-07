package com.violadin.debtorpit.ui.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.violadin.debtorpit.R
import com.violadin.debtorpit.domain.model.Person
import com.violadin.debtorpit.presentation.viewmodel.PersonViewModel
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.bottom_sheet_header_add_person.*
import kotlinx.android.synthetic.main.bottom_sheet_info_person_fragment.*
import android.widget.LinearLayout

import android.widget.EditText
import androidx.core.view.marginStart
import kotlinx.android.synthetic.main.bottom_sheet_header_add_person.button_cancel
import kotlinx.android.synthetic.main.bottom_sheet_header_info_person.*

class BottomSheetInfoPersonFragment(
    val viewModel: PersonViewModel
): BottomSheetDialogFragment() {

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

        if (person?.phone.isNullOrEmpty()) {
            phone_text.visibility = View.GONE
            call_person.visibility = View.GONE
        } else {
            phone_text.text = person!!.phone
            call_person.setOnClickListener {
                it.apply { isEnabled = false; postDelayed({isEnabled = true}, 1000) }
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:${phone_text.text}")
                startActivity(intent)
            }
        }

        first_name_text.text = person!!.firstName
        if (person?.lastName.isNullOrEmpty())
            last_name_text.visibility = View.GONE
        else
            last_name_text.text = person!!.lastName
        debt_count.text = person!!.debt.toString()

        edit_debt_button.setOnClickListener {
            it.apply { isEnabled = false; postDelayed({isEnabled = true}, 1000) }
            showEditDebtDialog()
        }

        delete_person_button.setOnClickListener {
            it.apply { isEnabled = false; postDelayed({isEnabled = true}, 1000) }
            showDeleteDialog()
        }

        drop_debt_button.setOnClickListener {
            it.apply { isEnabled = false; postDelayed({isEnabled = true}, 1000) }
            updateDebtOfPerson(0.0)
        }

        button_cancel.setOnClickListener {
            it.apply { isEnabled = false; postDelayed({isEnabled = true}, 1000) }
            dismiss()
        }
    }

    @SuppressLint("CheckResult")
    private fun updateDebtOfPerson(debt: Double) {
        Flowable.fromCallable {
            viewModel.updatePerson(person!!.id!!, debt)
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe {
            debt_count.text = debt.toString()
            person!!.debt = debt
        }
    }

    @SuppressLint("CheckResult")
    private fun showEditDebtDialog() {
        val input = EditText(context)
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        input.layoutParams = lp
        input.inputType = InputType.TYPE_CLASS_NUMBER
        input.hint = requireContext().getString(R.string.debt_hint)
        val dialog = AlertDialog.Builder(context)
            .setTitle(R.string.change_debt_title)
            .setPositiveButton(R.string.debt_add) { dialog, which ->
                if (input.text.isNotEmpty()) {
                    val newDebt = person!!.debt!! + input.text.toString().toDouble()
                    updateDebtOfPerson(newDebt)
                    dialog.cancel()
                }
            }
            .setNegativeButton(R.string.debt_remove) { dialog, which ->
                if (input.text.isNotEmpty()) {
                    val newDebt = person!!.debt!! - input.text.toString().toDouble()
                    if (newDebt < 0.0)
                        updateDebtOfPerson(0.0)
                    else
                        updateDebtOfPerson(newDebt)
                }
                dialog.cancel()
            }
            .setNeutralButton(R.string.cancel) { dialog, which ->
                dialog.cancel()
            }
        dialog.setView(input)
        dialog.show()
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        input.postDelayed({
            input.requestFocus()
            imm!!.showSoftInput(input, 0)
        }, 100)
    }

    @SuppressLint("CheckResult")
    private fun showDeleteDialog() {
        val dialog = AlertDialog.Builder(context)
            .setMessage(R.string.deleting_message)
            .setPositiveButton(R.string.delete) { dialog, which ->
                Flowable.fromCallable {
                        viewModel.deletePerson(person!!)
                }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe {
                    dismiss()
                }
                dialog.cancel()
            }
            .setNegativeButton(R.string.cancel) { dialog, which ->
                dialog.cancel()
            }
            .show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(requireContext().getColor(R.color.red_base))
    }

    override fun getTheme(): Int =
        R.style.AppBottomSheetDialogTheme
}