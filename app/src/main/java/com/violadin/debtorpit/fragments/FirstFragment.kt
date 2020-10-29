package com.violadin.debtorpit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.violadin.debtorpit.R

class FirstFragment: Fragment() {

    lateinit var str: String

    companion object {
        public fun newInstance(): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putString("1", "123123")
            fragment.arguments.apply { val args1 = args }
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null)
            str = arguments!!.getString("1").toString()
    }

    public override fun onCreateView(inflater: LayoutInflater,
                                     container: ViewGroup?,
                                     savedInstanceState: Bundle?):
            View? {
        return inflater.inflate(R.layout.firstfragment_activity, container, false)
    }

}