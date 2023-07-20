package com.example.zupzup_manager.ui.setting

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.zupzup_manager.databinding.FragmentSettingBinding
import com.example.zupzup_manager.ui.common.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : Fragment() {

    private lateinit var binding: FragmentSettingBinding
    private val settingViewModel: SettingViewModel by viewModels()

    private val settingClickListener = object : SettingClickListener {
        override fun onToggleBtnClick() {
            Log.e("ok", ".value.toString()")
            settingViewModel.changeStoreStatus()
        }

        override fun navigateToStore() {
            val store =
                settingViewModel.storeInfo.value
            val action =
                SettingFragmentDirections.actionFragSettingToStoreFragment(store)
            findNavController().navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
    }

    private fun initBinding() {
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = settingViewModel
            clickListener = settingClickListener
        }
    }

    interface SettingClickListener {
        fun onToggleBtnClick()
        fun navigateToStore()
    }
}