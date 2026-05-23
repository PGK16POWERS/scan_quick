package com.example.scan_quick

import android.content.Context.VIBRATOR_SERVICE
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.fragment.findNavController
import com.example.scan_quick.utils.UtilsFile

class home : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val vibrator = requireActivity().getSystemService(Vibrator::class.java)
        val startScanningBtn = view.findViewById<LinearLayout>(R.id.start_scanning)
        val effect = VibrationEffect.createOneShot(60, VibrationEffect.DEFAULT_AMPLITUDE)

        startScanningBtn.setOnClickListener { _ ->
            vibrator.vibrate(effect)
            UtilsFile.notify(requireContext(),"scan", "Started")
            findNavController().navigate(R.id.scan)
        }

        val prod = view.findViewById<LinearLayout>(R.id.product_1)
        val prod2 = view.findViewById<LinearLayout>(R.id.product_2)
        val prod3 = view.findViewById<LinearLayout>(R.id.product_3)

        prod.setOnClickListener { _ ->
            findNavController().navigate(R.id.product)
        }

        prod2.setOnClickListener { _ ->
            findNavController().navigate(R.id.product)
        }

        prod3.setOnClickListener { _ ->
            findNavController().navigate(R.id.product)
        }

        return view
    }

}