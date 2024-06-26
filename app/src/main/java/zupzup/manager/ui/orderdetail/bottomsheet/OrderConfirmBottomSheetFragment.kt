package zupzup.manager.ui.orderdetail.bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import zupzup.manager.R
import zupzup.manager.databinding.FragmentOrderConfirmBottomsheetBinding
import zupzup.manager.domain.models.order.OrderModel
import zupzup.manager.ui.common.toDecimalFormat
import zupzup.manager.ui.orderdetail.OrderDetailFragmentClickListener

class OrderConfirmBottomSheetFragment(
    private val confirmedOrder: OrderModel,
    private val isPartialConfirm: Boolean,
    private val orderHandler: OrderDetailFragmentClickListener
) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentOrderConfirmBottomsheetBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), R.style.BottomSheetStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentOrderConfirmBottomsheetBinding.inflate(layoutInflater, container, false)
        dialog?.setCanceledOnTouchOutside(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
        addDynamicCartList()
    }

    private fun addDynamicCartList() {
        confirmedOrder.orderList.forEach {
            val layout = requireActivity().layoutInflater.inflate(
                R.layout.item_order_confirm_bottom_sheet_cart,
                null
            )
            val tvName: TextView = layout.findViewById(R.id.tv_item_name)
            val tvItemPrice: TextView = layout.findViewById(R.id.tv_item_price)
            tvName.text = "${it.itemName} ${it.itemCount}개"
            tvItemPrice.text = (it.salePrice * it.itemCount).toDecimalFormat()
            binding.linearLayoutCartListContainer.addView(layout)
        }
    }

    private fun initBinding() {
        with(binding) {
            order = confirmedOrder
            isPartial = isPartialConfirm
            handler = orderHandler
        }
    }
}