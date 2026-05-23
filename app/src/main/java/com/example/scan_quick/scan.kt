package com.example.scan_quick

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.fonts.FontFamily
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.FOCUSABLE
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController

class scan : Fragment() {

    private lateinit var previewView: PreviewView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_scan, container, false)

        previewView = view.findViewById(R.id.camera_temp)

        if ( ContextCompat.checkSelfPermission( requireContext(), Manifest.permission.CAMERA ) == PackageManager.PERMISSION_GRANTED) {
            startCamera(previewView)
        } else {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), 10)
        }

        val backButton = view.findViewById<ImageView>(R.id.back_button)

        backButton.setOnClickListener { _->
            findNavController().navigate(R.id.home2)
        }

        val scanBlock = view.findViewById<LinearLayout>(R.id.scan_block)
        val manualInputBlock = view.findViewById<LinearLayout>(R.id.manual_input_block)
        val manualInputButton = view.findViewById<AppCompatButton>(R.id.manual_input_button)
        val cancelButton = view.findViewById<AppCompatButton>(R.id.cancel_one)
        val cancelButtonTwo = view.findViewById<AppCompatButton>(R.id.cancel_two)

        manualInputBlock.visibility = View.GONE

        manualInputButton.setOnClickListener { _ ->
            manualInputBlock.visibility = View.VISIBLE
            scanBlock.visibility = View.GONE
        }

        cancelButton.setOnClickListener { _ ->
            manualInputBlock.visibility = View.GONE
            scanBlock.visibility = View.VISIBLE
        }

        cancelButtonTwo.setOnClickListener { _ ->
            prompt(view)
        }

        return view
    }

    private fun startCamera(previewView: PreviewView) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build()
            preview.setSurfaceProvider(previewView.surfaceProvider)
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                viewLifecycleOwner,
                cameraSelector,
                preview
            )
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (
            requestCode == 10 &&
            grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            startCamera(previewView)
        }
    }

    private fun prompt(view: View) {
        val parent = requireActivity().window.decorView as ViewGroup

        fun dp(value:Int) = (value * resources.displayMetrics.density).toInt()

        val overlay = LinearLayout(requireContext()).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            gravity = Gravity.CENTER
            setPadding(dp(25),dp(25),dp(25),dp(25))
            background = ContextCompat.getDrawable(requireContext(),R.drawable.overlay)
            isClickable = true
            isFocusable = true
        }

        val prompt = LinearLayout(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(dp(300),LinearLayout.LayoutParams.WRAP_CONTENT)
            setPadding(dp(22),dp(18),dp(22),dp(18))
            background = ContextCompat.getDrawable(requireContext(),R.drawable.prompt)
        }

        val promptHeader = TextView(requireContext()).apply{
            text = "Navigate back?"
            setTextColor(Color.argb(255,0,0,0))
            typeface = ResourcesCompat.getFont(requireContext(), R.font.uber_move_text_bold)
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
        }


        prompt.addView(promptHeader)
        overlay.addView(prompt)

        parent.addView(overlay)
    }



}