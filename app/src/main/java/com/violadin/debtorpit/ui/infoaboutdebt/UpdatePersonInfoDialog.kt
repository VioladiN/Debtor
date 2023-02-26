package com.violadin.debtorpit.ui.infoaboutdebt

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.violadin.debtorpit.databinding.DialogUpdatePersonInfoBinding

class UpdatePersonInfoDialog(
    context: Context,
    val buttonListener: (String, String) -> Unit
) : Dialog(context) {

    private lateinit var binding: DialogUpdatePersonInfoBinding
    private var fio: String? = null
    private var phone: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogUpdatePersonInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initDialog()
        window?.decorView?.setBackgroundResource(android.R.color.transparent)
    }

    private fun initDialog() {
        with(binding) {
            fio?.let { fio -> fioEd.setText(fio) }
            phone?.let { phone -> phoneEd.setText(phone) }

            dialogClose.setOnClickListener {
                dismiss()
            }

            changeInfoButton.setOnClickListener {
                buttonListener(fioEd.text.toString(), phoneEd.text.toString())
                dismiss()
            }

            imageViewAccept.setOnClickListener {
                buttonListener(fioEd.text.toString(), phoneEd.text.toString())
                dismiss()
            }
        }
    }

    fun show(
        fio: String,
        phone: String
    ) {
        this.fio = fio
        this.phone = phone
        super.show()
    }
}