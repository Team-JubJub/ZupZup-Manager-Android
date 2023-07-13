package com.example.zupzup_manager.ui.management

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.zupzup_manager.R
import com.example.zupzup_manager.databinding.FragmentManagementBottomSheetBinding
import com.example.zupzup_manager.ui.management.clicklistener.ManagementDialogClickListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ManagementStateBottomSheetFragment(
    private val managementDialogClickListener: ManagementDialogClickListener
) : BottomSheetDialogFragment() {

    private var _binding: FragmentManagementBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), R.style.BottomSheetStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManagementBottomSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
    }

    private fun initBinding() {
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            clickListener = managementDialogClickListener
        }
    }

}