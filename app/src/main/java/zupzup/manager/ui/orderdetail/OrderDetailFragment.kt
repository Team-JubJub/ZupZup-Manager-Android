package zupzup.manager.ui.orderdetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import zupzup.manager.databinding.FragmentOrderDetailBinding
import zupzup.manager.domain.models.order.OrderModel
import zupzup.manager.ui.common.UiEventState
import zupzup.manager.ui.common.progress.ProgressDialogFragment
import zupzup.manager.ui.orderdetail.binding.OrderDetailBindingHelper
import zupzup.manager.ui.orderdetail.bottomsheet.OrderConfirmBottomSheetFragment
import zupzup.manager.ui.orderdetail.recyclerview.OrderDetailItemDecorator
import zupzup.manager.ui.orderdetail.recyclerview.OrderDetailRcvAdapter
import zupzup.manager.ui.orderlist.OrderListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import zupzup.manager.ui.orderdetail.models.OrderDetailHeaderModel
import zupzup.manager.ui.orderdetail.models.OrderDetailViewType

@AndroidEntryPoint
class OrderDetailFragment : Fragment() {

    private val args: OrderDetailFragmentArgs by navArgs()
    private var _binding: FragmentOrderDetailBinding? = null
    private val binding get() = _binding!!
    private val orderDetailViewModel: OrderDetailViewModel by viewModels()

    private var orderConfirmBottomSheet: OrderConfirmBottomSheetFragment? = null
    private val progressDialog = ProgressDialogFragment()

    private val orderDetailBindingHelper = OrderDetailBindingHelper(
        ::onOrderConfirmButtonClickListener,
        { itemId: Long -> orderDetailViewModel.plusConfirmedAmount(itemId) },
        { itemId: Long -> orderDetailViewModel.minusConfirmedAmount(itemId) }
    )

    private val orderHandler = object : OrderDetailFragmentClickListener {
        override fun confirmOrder(orderModel: OrderModel, isPartial: Boolean) {
            orderDetailViewModel.confirmOrder(orderModel, isPartial)
        }

        override fun rejectOrder(orderModel: OrderModel) {
            orderDetailViewModel.rejectOrder(orderModel)
        }

        override fun cancelOrder(orderDetailHeader: OrderDetailHeaderModel, orderDetailBody: List<OrderDetailViewType>) {
            val orderModel = orderDetailBindingHelper.createOrderModel(orderDetailHeader, orderDetailBody)
            orderDetailViewModel.cancelOrder(orderModel)
        }

        override fun completeOrder(orderId: Long) {
            orderDetailViewModel.completeOrder(orderId)
        }

        override fun onBackBtnClick() {
            findNavController().popBackStack()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentOrderDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setArgsToViewModel()
        initBinding()
        initRecyclerView()
        collectOrderProcessEventState()
    }

    private fun initRecyclerView() {
        with(binding.rcvOrder) {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(OrderDetailItemDecorator())
        }
    }

    private fun setArgsToViewModel() {
        orderDetailViewModel.setArgsToViewModel(args.order)
    }

    private fun onOrderConfirmButtonClickListener(
        order: OrderModel,
        isPartial: Boolean
    ) {
        orderConfirmBottomSheet =
            OrderConfirmBottomSheetFragment(order, isPartial, orderHandler)
        orderConfirmBottomSheet!!.show(parentFragmentManager, null)
    }

    private fun initBinding() {
        with(binding) {
            adapter = OrderDetailRcvAdapter(
                orderDetailBindingHelper
            )
            handler = orderHandler
            viewModel = orderDetailViewModel
            lifecycleOwner = viewLifecycleOwner
            bindingHelper = orderDetailBindingHelper
        }
    }

    private fun collectOrderProcessEventState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                orderDetailViewModel.orderProcessingUiState.collect { eventState ->
                    when (eventState) {
                        is UiEventState.Processing -> {
                            progressDialog.show(parentFragmentManager, null)
                        }
                        is UiEventState.Complete -> {
                            if (orderConfirmBottomSheet != null) {
                                orderConfirmBottomSheet!!.dismiss()
                            }
                            progressDialog.dismiss()
                            findNavController().popBackStack()
                        }
                        is UiEventState.Fail -> {
                            if (orderConfirmBottomSheet != null) {
                                orderConfirmBottomSheet!!.dismiss()
                            }
                            progressDialog.dismiss()
                            Toast.makeText(
                                requireContext(),
                                eventState.errorMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}