package zupzup.manager.ui.item

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import zupzup.manager.R
import zupzup.manager.databinding.FragmentItemBinding
import zupzup.manager.domain.models.item.ItemModel
import zupzup.manager.domain.models.item.ItemQuantityModel
import zupzup.manager.ui.common.ManagementState
import zupzup.manager.ui.common.fromDpToPx
import zupzup.manager.ui.custom.GridSpacingItemDecoration
import zupzup.manager.ui.item.clicklistener.ItemBtnClickListener
import zupzup.manager.ui.item.clicklistener.ItemDialogClickListener
import zupzup.manager.ui.item.recyclerview.ItemRcvAdapter

@AndroidEntryPoint
class ItemFragment : Fragment() {
    private var _binding: zupzup.manager.databinding.FragmentItemBinding? = null
    private val binding get() = _binding!!
    private var itemStateBottomSheetFragment: ItemManagementStateBottomSheetFragment? = null
    private val itemViewModel: ItemViewModel by activityViewModels()
    private lateinit var navigationBarVisibilityListener: NavigationBarVisibilityListener

    // Dialog 내부에서 클릭할 때 모드 전환하는 클릭 리스너
    private val itemDialogClickListener = object : ItemDialogClickListener {
        override fun changeState(state: String) {
            if (itemViewModel.managementUiState.value is ManagementState.AmountMode && state == "DefaultMode") {
                restoreModifiedAmountItem()
                itemViewModel.restoreItemDetailBody()
            }
            itemViewModel.changeState(state)
        }

        override fun navigateToItemAdd() {
            if (itemStateBottomSheetFragment != null) {
                itemStateBottomSheetFragment!!.dismiss()
            }
            val action =
                ItemFragmentDirections.actionFragItemToItemDetailFragment()
            findNavController().navigate(action)
        }
    }

    private fun restoreModifiedAmountItem() {
        with(binding) {
            rcvManagement.children.forEachIndexed { index, view ->
                val itemList = itemViewModel.itemDetailBody.value
                if (itemList[index].itemCount != itemList[index].modifiedStock) {
                    view.findViewById<TextView>(R.id.tv_item_amount).text =
                        itemList[index].itemCount.toString()
                }

            }
        }
    }

    private val itemBtnClickListener = object : ItemBtnClickListener {
        override fun createBottomSheet() {
            itemStateBottomSheetFragment =
                ItemManagementStateBottomSheetFragment(itemDialogClickListener)
            itemStateBottomSheetFragment!!.show(parentFragmentManager, "")
        }

        override fun onPlusItemModifiedAmountBtnClick(itemId: Long) {
            itemViewModel.plusModifiedAmount(itemId)
        }

        override fun onMinusItemModifiedAmountBtnClick(itemId: Long) {
            itemViewModel.minusModifiedAmount(itemId)
        }

        // 하단 수정 완료 버튼 클릭 이벤트 -> 수량 수정에서만 작동함!
        override fun modifyItemQuantity(state: String) {
            if (state.contains("AmountMode")) {
                AlertDialog.Builder(requireContext())
                    .setTitle("제품 수량 수정하기")
                    .setMessage("제품 수량을 수정합니다.")
                    .setPositiveButton("확인") { _, _ ->
                        Log.d("제품 수량 수정", "확인")
                        val itemQuantityList = itemViewModel.itemDetailBody.value.map { item ->
                            ItemQuantityModel(item.itemId, item.modifiedStock)
                        }
                        itemViewModel.modifyItemQuantity(itemQuantityList)
                        itemViewModel.changeState("DefaultMode")
                    }
                    .setNegativeButton("취소") { _, _ ->
                        Log.d("제품 수량 수정", "취소")

                    }
                    .create()
                    .show()
            } else {
                itemViewModel.changeState("DefaultMode")
            }
        }

        override fun navigateToItemModify(item: ItemModel) {
            val action =
                ItemFragmentDirections.actionFragItemToItemDetailFragment(
                    item
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
        _binding = FragmentItemBinding.inflate(inflater)
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
                launch {
                    itemViewModel.managementUiState.collect {
                        when (it) {
                            ManagementState.DefaultMode -> {
                                navigationBarVisibilityListener.setNavigationBarVisibility(true)
                            }

                            ManagementState.AmountMode -> {
                                if (itemStateBottomSheetFragment != null) {
                                    itemStateBottomSheetFragment!!.dismiss()
                                }
                                navigationBarVisibilityListener.setNavigationBarVisibility(false)
                            }

                            ManagementState.InfoMode -> {
                                if (itemStateBottomSheetFragment != null) {
                                    itemStateBottomSheetFragment!!.dismiss()
                                }
                                navigationBarVisibilityListener.setNavigationBarVisibility(false)
                            }
                        }
                    }
                }
                launch {
                    itemViewModel.itemDetailBody.collect {
                        if (it.isEmpty()) {
                            binding.linearLayoutEmptyIcon.visibility = View.VISIBLE
                        }
                        else {
                            binding.linearLayoutEmptyIcon.visibility = View.GONE
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
            adapter = ItemRcvAdapter(
                itemBtnClickListener,
                itemViewModel,
                viewLifecycleOwner
            )
            viewModel = itemViewModel
            lifecycleOwner = viewLifecycleOwner
            stateClickListener = itemDialogClickListener
            dialogClickListener = itemBtnClickListener
            refresh.setOnRefreshListener {
                refresh.isRefreshing = false
                itemViewModel.getItemList()
            }
        }
    }

    private fun initRecyclerView() {
        with(binding.rcvManagement) {
            layoutManager = GridLayoutManager(requireContext(), 2)
            addItemDecoration(GridSpacingItemDecoration(2, 8f.fromDpToPx()))
            itemAnimator = null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}