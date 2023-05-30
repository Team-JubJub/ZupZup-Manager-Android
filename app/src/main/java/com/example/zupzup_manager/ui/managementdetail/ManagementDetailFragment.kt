package com.example.zupzup_manager.ui.managementdetail

import android.os.Bundle
import android.util.Log
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
import com.example.zupzup_manager.domain.models.ReservationModel
import com.example.zupzup_manager.domain.models.StoreModel
import com.example.zupzup_manager.ui.managementdetail.binding.ManagementDetailBindingHelper
import com.example.zupzup_manager.ui.managementdetail.recyclerview.ManagementDetailRcvAdapter
import com.example.zupzup_manager.ui.reservationdetail.binding.ReservationDetailBindingHelper
import com.example.zupzup_manager.ui.reservationdetail.bottomsheet.ReservationConfirmBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManagementDetailFragment : Fragment() {

    private val managementDetailViewModel: ManagementDetailViewModel by viewModels()
    private lateinit var binding: FragmentManagementDetailBinding

    private val args: ManagementDetailFragmentArgs by navArgs()
    private val managementDetailBindingHelper = ManagementDetailBindingHelper(
        ::onCreateStoreModifyDialogButtononClick,
        { itemId: Long -> managementDetailViewModel.plusModifiedAmount(itemId) },
        { itemId: Long -> managementDetailViewModel.minusModifiedAmount(itemId) },
        ::navigateToBackStack,
        ::navigateToMerchandiseAdd,
        ::navigateToMerchandiseModify
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentManagementDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
        initRecyclerView()
        setManagementDetail()
    }

    private fun setManagementDetail() {
        val merchandiseList = args.store.merchandiseList
        managementDetailViewModel.setMerchandiseList(merchandiseList)
    }

    private fun navigateToBackStack() {
        findNavController().popBackStack()
    }

    private fun navigateToMerchandiseAdd() {
        val action =
            ManagementDetailFragmentDirections.actionManagementDetailFragmentToMerchandiseDetailFragment()
        findNavController().navigate(action)
    }

    private fun navigateToMerchandiseModify(merchandise: MerchandiseModel) {
        Log.e("error", "err")
        val action =
            ManagementDetailFragmentDirections.actionManagementDetailFragmentToMerchandiseDetailFragment(
                merchandise
            )
        findNavController().navigate(action)
    }

    private fun onCreateStoreModifyDialogButtononClick(merchandiseList: List<MerchandiseModel>) {
        val dlg = ModifyAlertDialog(this.requireContext())
        dlg.listener = object: ModifyAlertDialog.MerchandiseDialogClickedListener {
            override fun onClicked() {
                managementDetailViewModel.modifyMerchandise(merchandiseList)
            }
        }
        dlg.modify()
    }

    private fun initRecyclerView() {
        with(binding) {
            rcvMerchandiseList.layoutManager = LinearLayoutManager(context)
            adapter =
                ManagementDetailRcvAdapter(managementDetailBindingHelper)
        }
    }

    private fun initBinding() {
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = managementDetailViewModel
            bindingHelper = managementDetailBindingHelper
        }
    }
}