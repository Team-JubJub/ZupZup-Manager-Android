package com.example.zupzup_manager.ui.item

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.zupzup_manager.R
import com.example.zupzup_manager.databinding.FragmentItemBottomSheetBinding
import com.example.zupzup_manager.ui.item.clicklistener.ItemDialogClickListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ItemManagementStateBottomSheetFragment(
    private val itemDialogClickListener: ItemDialogClickListener
) : BottomSheetDialogFragment() {

    private var _binding: FragmentItemBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), R.style.BottomSheetStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemBottomSheetBinding.inflate(inflater)
        dialog?.setCanceledOnTouchOutside(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
    }

    private fun initBinding() {
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            clickListener = itemDialogClickListener
        }
    }

}