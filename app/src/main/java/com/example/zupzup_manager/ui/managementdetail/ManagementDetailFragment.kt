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
import com.example.zupzup_manager.domain.models.StoreModel
import com.example.zupzup_manager.ui.managementdetail.recyclerview.ManagementDetailRcvAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManagementDetailFragment : Fragment() {

    private val managementDetailViewModel: ManagementDetailViewModel by viewModels()
    private lateinit var binding: FragmentManagementDetailBinding

    private val args: ManagementDetailFragmentArgs by navArgs()

    private fun alertDialogListener(storeModel: StoreModel) {
        val dlg = ModifyAlertDialog(this.requireContext())
        dlg.listener = object: ModifyAlertDialog.MerchandiseDialogClickedListener {
            override fun onClicked() {
                managementDetailViewModel.modifyMerchandise(storeModel)
            }
        }
        dlg.modify()
    }

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
        getManagementDetailById()
    }

    private fun getManagementDetailById() {
        val storeId = args.storeId
        managementDetailViewModel.getMerchandiseList(storeId)
    }

    private fun navigateToMerchandiseDetail(merchandise: MerchandiseModel) {
        val action =
            ManagementDetailFragmentDirections.actionManagementDetailFragmentToMerchandiseDetailFragment(
                merchandise
            )
        findNavController().navigate(action)
    }

    private fun initRecyclerView() {
        with(binding.rcvMerchandiseList) {
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun initBinding() {
        with(binding) {
            adapter = ManagementDetailRcvAdapter(this@ManagementDetailFragment::navigateToMerchandiseDetail)
            lifecycleOwner = viewLifecycleOwner
            viewModel = managementDetailViewModel
            //store = storemodel 업데이트 하는 부분 model 어떻게 넘길지
            alertDialog = ::alertDialogListener
        }
    }
}