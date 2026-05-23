package com.example.scan_quick

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.fragment.findNavController

class product : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_product, container, false)

        val backButton = view.findViewById<ImageView>(R.id.back_button)

        backButton.setOnClickListener { _ ->
            findNavController().navigate(R.id.home2)
        }

        return view
    }

}