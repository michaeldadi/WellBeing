package com.cis102y.wellbeing.ui.dashboard.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cis102y.wellbeing.R
import kotlinx.android.synthetic.main.fragment_orders.*

class OrdersFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_orders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btn_goto_upload_prescription.setOnClickListener{
            findNavController().navigate(R.id.navigate_upload_prescription)
        }
    }
}
