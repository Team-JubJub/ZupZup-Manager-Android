package com.example.zupzup_manager.ui.management

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.zupzup_manager.databinding.FragmentManagementBinding
import com.example.zupzup_manager.domain.models.MerchandiseModel
import com.example.zupzup_manager.ui.common.ManagementState
import com.example.zupzup_manager.ui.common.fromDpToPx
import com.example.zupzup_manager.ui.custom.GridSpacingItemDecoration
import com.example.zupzup_manager.ui.management.clicklistener.ManagementBtnClickListener
import com.example.zupzup_manager.ui.management.clicklistener.ManagementDialogClickListener
import com.example.zupzup_manager.ui.management.recyclerview.ManagementRcvAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ManagementFragment : Fragment() {
    private var _binding: FragmentManagementBinding? = null
    private val binding get() = _binding!!
    private var managementStateBottomSheetFragment: ManagementStateBottomSheetFragment? = null
    private val managementViewModel: ManagementViewModel by viewModels()

    private val managementDialogClickListener = object : ManagementDialogClickListener {
        override fun changeState(state: String) {
            println("$state state!")
            println(managementViewModel.managementUiState.value)
            println(managementViewModel.managementUiState.toString())
            println(ManagementState.Amount.toString())
            managementViewModel.changeState(state)
        }
    }

    private val managementBtnClickListener = object : ManagementBtnClickListener {
        override fun createBottomSheet() {
            managementStateBottomSheetFragment = ManagementStateBottomSheetFragment(managementDialogClickListener)
            managementStateBottomSheetFragment!!.show(parentFragmentManager, "")
        }

        override fun onPlusMerchandiseModifiedAmountBtnClick(itemId: Long) {
            managementViewModel.plusModifiedAmount(itemId)
        }

        override fun onMinusMerchandiseModifiedAmountBtnClick(itemId: Long) {
            managementViewModel.minusModifiedAmount(itemId)
        }

        override fun modifyMerchandise(merchandiseList: List<MerchandiseModel>) {
            managementViewModel.modifyMerchandise(merchandiseList)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManagementBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
        initRecyclerView()
        collectManagementState()
    }

    private fun collectManagementState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                managementViewModel.managementUiState.collect {
                    when (it) {
                        ManagementState.Default -> {
                            binding.btnBack.visibility = View.GONE
                        }
                        ManagementState.Amount -> {
                            if (managementStateBottomSheetFragment != null) {
                                managementStateBottomSheetFragment!!.dismiss()
                            }
                            binding.btnBack.visibility = View.VISIBLE
                        }
                        ManagementState.Info -> {
                            if (managementStateBottomSheetFragment != null) {
                                managementStateBottomSheetFragment!!.dismiss()
                            }
                            binding.btnBack.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun initBinding() {
        with(binding) {
            adapter = ManagementRcvAdapter(managementBtnClickListener, managementViewModel)
            viewModel = managementViewModel
            lifecycleOwner = viewLifecycleOwner
            stateClickListener = managementDialogClickListener
            dialogClickListener = managementBtnClickListener

        }
    }

    private fun initRecyclerView() {
        with(binding.rcvManagement) {
            layoutManager = GridLayoutManager(requireContext(), 2)
            addItemDecoration(GridSpacingItemDecoration(2, 8f.fromDpToPx()))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}