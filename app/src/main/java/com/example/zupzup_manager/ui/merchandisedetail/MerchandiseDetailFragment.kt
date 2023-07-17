package com.example.zupzup_manager.ui.merchandisedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.zupzup_manager.databinding.FragmentMerchandiseDetailBinding
import com.example.zupzup_manager.ui.management.ManagementViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MerchandiseDetailFragment : Fragment() {

    private lateinit var binding: FragmentMerchandiseDetailBinding
    private val managementViewModel: ManagementViewModel by viewModels()

    private val merchandiseDetailClickListener = object : MerchandiseDetailClickListener {
        override fun onPlusMerchandiseModifiedAmountBtnClick(itemId: Long) {
            managementViewModel.plusModifiedAmount(itemId)
        }

        override fun onMinusMerchandiseModifiedAmountBtnClick(itemId: Long) {
            managementViewModel.minusModifiedAmount(itemId)
        }

        override fun navigateToBackStack() {
            findNavController().popBackStack()
        }

        override fun modifyMerchandise() {
            // TODO : 파베에서는 문서 업데이트만 가능하며, 배열 내부에 접근해서 업데이트할 수는 없음
            //managementViewModel.modifyMerchandiseList(managementViewModel.managementDetailBody.value)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMerchandiseDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
    }

    private fun initBinding() {
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            clickListener = merchandiseDetailClickListener
            merchandise = MerchandiseDetailFragmentArgs.fromBundle(requireArguments()).merchandise
        }
    }
}