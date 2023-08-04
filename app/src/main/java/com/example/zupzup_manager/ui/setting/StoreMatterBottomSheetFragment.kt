package com.example.zupzup_manager.ui.setting

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.zupzup_manager.R
import com.example.zupzup_manager.databinding.FragmentStoreMatterBottomsheetBinding
import com.example.zupzup_manager.ui.common.User
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoreMatterBottomSheetFragment(
    private val storeMatterValue: String
) : BottomSheetDialogFragment() {

    private val settingViewModel: SettingViewModel by viewModels()
    private lateinit var binding: FragmentStoreMatterBottomsheetBinding

    private val storeMatterClickListener = object : StoreMatterClickListener {
        override fun modifyStoreMatter(storeMatter: String) {
            settingViewModel.modifyStoreMatter(storeMatter)
        }
    }

    private var onDismissListener: DialogInterface.OnDismissListener? = null
    fun setOnDismissListener(onDismissListener: DialogInterface.OnDismissListener) {
        this.onDismissListener = onDismissListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), R.style.BottomSheetStyle).apply {
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentStoreMatterBottomsheetBinding.inflate(layoutInflater, container, false)
        dialog?.setCanceledOnTouchOutside(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
        closeDialog()
    }

    private fun closeDialog() {
        lifecycleScope.launchWhenStarted {
            settingViewModel.closeDialogEvent.collect {
                dismiss()
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismissListener?.onDismiss(dialog)
    }

    private fun initBinding() {
        with(binding) {
            storeMatter = storeMatterValue
            clickListener = storeMatterClickListener
        }
    }

    interface StoreMatterClickListener {
        fun modifyStoreMatter(storeMatter: String)
    }
}