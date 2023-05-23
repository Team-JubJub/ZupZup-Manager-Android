package com.example.zupzup_manager.ui.common.progress

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.zupzup_manager.databinding.FragmentProgressDialogBinding

class ProgressDialogFragment() : DialogFragment() {
    private var _binding: FragmentProgressDialogBinding? = null
    private val binding get() = _binding!!

    private var showState = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProgressDialogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    fun showProgressDialog(fragmentManager: FragmentManager) {
        showState = true
        this.show(fragmentManager, null)
    }

    fun hideProgressDialog() {
        if (showState) {
            this.dismiss()
            showState = false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}