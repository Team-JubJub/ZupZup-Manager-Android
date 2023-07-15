package com.example.zupzup_manager.ui.management

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
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
    private lateinit var navigationBarVisibilityListener: NavigationBarVisibilityListener

    private val managementDialogClickListener = object : ManagementDialogClickListener {
        override fun changeState(state: String) {
            managementViewModel.changeState(state)
        }
        override fun navigateToMerchandiseAdd() {
            if (managementStateBottomSheetFragment != null) {
                managementStateBottomSheetFragment!!.dismiss()
            }
            val action =
                ManagementFragmentDirections.actionFragManagementToMerchandiseDetailFragment()
            findNavController().navigate(action)
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

        // 하단 수정 완료 버튼 (depth 들어가지 않은 상태 / 수량 수정 모드로 fragment는 그대로)
        override fun modifyMerchandiseList(state: String) {
            if (state.contains("AmountMode")) {
                managementViewModel.modifyMerchandiseList(managementViewModel.managementDetailBody.value)
            }
            managementViewModel.changeState("DefaultMode")
        }

        override fun navigateToMerchandiseModify(merchandise: MerchandiseModel) {
            val action =
                ManagementFragmentDirections.actionFragManagementToMerchandiseDetailFragment(
                    merchandise
                )
            findNavController().navigate(action)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NavigationBarVisibilityListener) {
            navigationBarVisibilityListener = context
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
                        ManagementState.DefaultMode -> {
                            navigationBarVisibilityListener.setNavigationBarVisibility(true)
                        }
                        ManagementState.AmountMode -> {
                            if (managementStateBottomSheetFragment != null) {
                                managementStateBottomSheetFragment!!.dismiss()
                            }
                            navigationBarVisibilityListener.setNavigationBarVisibility(false)
                        }
                        ManagementState.InfoMode -> {
                            if (managementStateBottomSheetFragment != null) {
                                managementStateBottomSheetFragment!!.dismiss()
                            }
                            navigationBarVisibilityListener.setNavigationBarVisibility(false)
                        }
                    }
                }
            }
        }
    }

    interface NavigationBarVisibilityListener {
        fun setNavigationBarVisibility(isVisible: Boolean)
    }

    private fun initBinding() {
        with(binding) {
            adapter = ManagementRcvAdapter(managementBtnClickListener, managementViewModel, viewLifecycleOwner)
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