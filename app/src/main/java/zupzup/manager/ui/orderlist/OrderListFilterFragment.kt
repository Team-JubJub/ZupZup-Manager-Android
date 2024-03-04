package zupzup.manager.ui.orderlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import zupzup.manager.databinding.FragmentOrderListFilterBinding
import zupzup.manager.domain.models.order.OrderModel
import zupzup.manager.ui.common.User
import zupzup.manager.ui.orderlist.recyclerview.OrderListItemDecorator
import zupzup.manager.ui.orderlist.recyclerview.OrderListRcvAdapter


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

    override fun onResume() {
        super.onResume()
        orderListViewModel.getOrderList(User.getStoreId())
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
            refresh.setOnRefreshListener {
                refresh.isRefreshing = false
                orderListViewModel.getOrderList(User.getStoreId())
            }
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