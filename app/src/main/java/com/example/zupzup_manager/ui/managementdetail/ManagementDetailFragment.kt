package com.example.zupzup_manager.ui.managementdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zupzup_manager.databinding.FragmentManagementDetailBinding
import com.example.zupzup_manager.domain.models.MerchandiseModel
import com.example.zupzup_manager.ui.managementdetail.recyclerview.ManagementDetailRcvAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManagementDetailFragment : Fragment() {

    private val managementDetailViewModel: ManagementDetailViewModel by viewModels()
    private lateinit var binding: FragmentManagementDetailBinding
//
//    private val args: ManagementDetailFragmentArgs by navArgs()
//
//    private val managementDetailBtnClickListener = object : ManagementDetailBtnClickListener {
//        override fun createMerchandiseModifyDialog(merchandiseDetailBody: List<MerchandiseModel>) {
//            onCreateStoreModifyDialogButtononClick(merchandiseDetailBody)
//        }
//
//        override fun onPlusMerchandiseModifiedAmountBtnClick(itemId: Long) {
//            managementDetailViewModel.plusModifiedAmount(itemId)
//        }
//
//        override fun onMinusMerchandiseModifiedAmountBtnClick(itemId: Long) {
//            managementDetailViewModel.minusModifiedAmount(itemId)
//        }
//
//        override fun navigateToBackStack() {
//            findNavController().popBackStack()
//        }
//
//        override fun navigateToMerchandiseAdd() {
//            val action =
//                ManagementDetailFragmentDirections.actionManagementDetailFragmentToMerchandiseDetailFragment()
//            findNavController().navigate(action)
//        }
//
//        override fun navigateToMerchandiseModify(merchandise: MerchandiseModel) {
//            val action =
//                ManagementDetailFragmentDirections.actionManagementDetailFragmentToMerchandiseDetailFragment(
//                    merchandise
//                )
//            findNavController().navigate(action)
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentManagementDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        initBinding()
//        initRecyclerView()
//        setManagementDetail()
    }

//    private fun setManagementDetail() {
//        val merchandiseList = args.store.merchandiseList
//        managementDetailViewModel.setMerchandiseList(merchandiseList)
//    }
//
//    private fun onCreateStoreModifyDialogButtononClick(merchandiseList: List<MerchandiseModel>) {
//        val dlg = ModifyAlertDialog(this.requireContext())
//        dlg.listener = object: ModifyAlertDialog.MerchandiseDialogClickedListener {
//            override fun onClicked() {
//                managementDetailViewModel.modifyMerchandise(merchandiseList)
//            }
//        }
//        dlg.modify()
//    }
//
//    private fun initRecyclerView() {
//        with(binding) {
//            rcvMerchandiseList.layoutManager = LinearLayoutManager(context)
//            adapter =
//                ManagementDetailRcvAdapter(managementDetailBtnClickListener)
//        }
//    }
//
//    private fun initBinding() {
//        with(binding) {
//            lifecycleOwner = viewLifecycleOwner
//            viewModel = managementDetailViewModel
//            clickListener = managementDetailBtnClickListener
//        }
//    }
}