package com.example.zupzup_manager.ui.orderlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zupzup_manager.databinding.FragmentOrderListFilterBinding
import com.example.zupzup_manager.domain.models.OrderModel
import com.example.zupzup_manager.ui.orderlist.recyclerview.OrderListItemDecorator
import com.example.zupzup_manager.ui.orderlist.recyclerview.OrderListRcvAdapter

class OrderListFilterFragment : Fragment() {

    private lateinit var binding: FragmentOrderListFilterBinding
    private var filterOption: String? = null
    private val orderListViewModel: OrderListViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderListFilterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getFilterOption()
        initBinding()
        initRecyclerView()
    }

    private fun getFilterOption() {
        arguments?.takeIf { it.containsKey("position") }?.apply {
            when (getInt("position")) {
                0 -> filterOption = "NEW"
                1 -> filterOption = "CONFIRM"
                2 -> filterOption = "COMPLETE"
            }
        }
    }

    private fun initRecyclerView() {
        with(binding.rcvOrderList) {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(OrderListItemDecorator())
        }
    }

    private fun initBinding() {
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = orderListViewModel
            adapter = OrderListRcvAdapter(::navigateToOrderDetail)
            filter = filterOption
        }
    }

    private fun navigateToOrderDetail(order: OrderModel) {
        val action =
            OrderListFragmentDirections.actionFragOrderListToFragOrderDetail(
                order
            )
        findNavController().navigate(action)
    }
}