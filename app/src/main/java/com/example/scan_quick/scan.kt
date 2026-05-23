package com.example.scan_quick

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatButton
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
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

        if (
            ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            startCamera(previewView)
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.CAMERA), 10
            )
        }

        val backButton = view.findViewById<ImageView>(R.id.back_button)

        backButton.setOnClickListener { _->
            findNavController().navigate(R.id.home2)
        }

        val scanBlock = view.findViewById<LinearLayout>(R.id.scan_block)
        val manualInputBlock = view.findViewById<LinearLayout>(R.id.manual_input_block)
        val manualInputButton = view.findViewById<AppCompatButton>(R.id.manual_input_button)
        val cancelButton = view.findViewById<AppCompatButton>(R.id.cancel_one)

        manualInputBlock.visibility = View.GONE

        manualInputButton.setOnClickListener { _ ->
            manualInputBlock.visibility = View.VISIBLE
            scanBlock.visibility = View.GONE
        }

        cancelButton.setOnClickListener { _ ->
            manualInputBlock.visibility = View.GONE
            scanBlock.visibility = View.VISIBLE
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
            )}, ContextCompat.getMainExecutor(requireContext()))
    }

}