package com.example.zupzup_manager.ui.setting

import android.content.Intent
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
import com.example.zupzup_manager.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SettingFragment : Fragment() {

    private lateinit var binding: FragmentSettingBinding
    private val settingViewModel: SettingViewModel by viewModels()
    private var storeMatterBottomSheet: StoreMatterBottomSheetFragment? = null

    private val settingClickListener = object : SettingClickListener {
        override fun navigateToStore() {
            val storeInfo = settingViewModel.storeInfo.value
            val action =
                SettingFragmentDirections.actionFragSettingToStoreFragment(storeInfo)
            findNavController().navigate(action)
        }

        override fun signout() {
            settingViewModel.signOut()
            Log.d("TAG", "로그아웃 완료 -> 액티비티 이동")
            val loginIntent = Intent(context, LoginActivity::class.java)
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK + Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(loginIntent)
            Log.d("TAG", "액티비티 이동 완료")
        }

        override fun modifyStoreMatter() {
            storeMatterBottomSheet =
                StoreMatterBottomSheetFragment(settingViewModel.storeInfo.value.saleMatters)
            storeMatterBottomSheet!!.setOnDismissListener {
                settingViewModel.getStoreInfo(User.getStoreId())
            }
            storeMatterBottomSheet!!.show(parentFragmentManager, null)
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

    override fun onResume() {
        super.onResume()
        settingViewModel.getStoreInfo(User.getStoreId())
        Log.d("resume test", "resume test")
    }

    private fun initBinding() {
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = settingViewModel
            clickListener = settingClickListener
            toggleBtn.setOnClickListener{
                settingViewModel.changeStoreStatus(toggleBtn.isChecked)
            }
        }
    }

    interface SettingClickListener {
        fun navigateToStore()
        fun signout()
        fun modifyStoreMatter()
    }
}