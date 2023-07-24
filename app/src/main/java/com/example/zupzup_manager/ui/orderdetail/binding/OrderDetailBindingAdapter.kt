package com.example.zupzup_manager.ui.orderdetail.binding

import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.zupzup_manager.R
import com.example.zupzup_manager.domain.models.OrderSpecificModel
import com.example.zupzup_manager.ui.common.OrderStateMapper
import com.example.zupzup_manager.ui.common.toDecimalFormat
import com.example.zupzup_manager.ui.common.toDetailDateFormat
import com.example.zupzup_manager.ui.common.toTimeFormat
import com.example.zupzup_manager.ui.orderdetail.recyclerview.OrderDetailRcvAdapter
import com.example.zupzup_manager.ui.orderdetail.models.OrderDetailViewType


@BindingAdapter("orderDetailBody")
fun bindOrderDetailListToRecyclerView(
    recyclerView: RecyclerView,
    dataList: List<OrderDetailViewType>
) {
    (recyclerView.adapter as OrderDetailRcvAdapter).submitList(dataList.toList())
}

@BindingAdapter("orderTitle", "orderStatus")
fun bindOrderTitleToTextView(
    textView: TextView,
    orderTitle: String,
    orderStatus: String
) {
    if (orderStatus == "NEW"){
        textView.text = "예약"
    } else {
        textView.text = orderTitle
    }
}

@BindingAdapter("orderStatus")
fun bindOrderStatusToTextView(
    textView: TextView,
    orderStatus: String
) {
    if (orderStatus == "NEW"){
        textView.visibility = View.GONE
    } else {
        (textView.background as GradientDrawable).setColor(
            ContextCompat.getColor(
                textView.context,
                OrderStateMapper.getStateBackgroundColor(orderStatus)
            )
        )

        textView.text = OrderStateMapper.getStateDescription(orderStatus)
    }
}

@BindingAdapter("detailOrderId")
fun bindOrderDateToTextView(
    textView: TextView,
    orderId: Long
) {
    textView.text = orderId.toDetailDateFormat()
}

@BindingAdapter("visitTime")
fun bindOrderVisitTimeToTextView(
    textView: TextView,
    visitTime: String
) {
    textView.text = visitTime.substring(0, 5)
}

@BindingAdapter("totalPrice")
fun bindOrderTotalPriceToTextView(
    textView: TextView,
    orderList: List<OrderSpecificModel>?
) {
    if (orderList != null) {
        textView.text = orderList.sumOf { it.itemPrice * it.itemCount }.toDecimalFormat()
    }
}

@BindingAdapter("bindingHelper", "itemId", "tvConfirmedAmount", "orderAmount")
fun bindBindingHelperToAmountButton(
    ivBtnModifyAmount: ImageView,
    bindingHelper: OrderDetailBindingHelper,
    itemId: Long,
    tvConfirmedAmount: TextView,
    orderAmount: Int
) {
    ivBtnModifyAmount.setOnClickListener {
        when (ivBtnModifyAmount.id) {
            R.id.btn_plus -> {
                if (tvConfirmedAmount.text.toString().toInt() < orderAmount) {
                    bindingHelper.onPlusOrderItemConfirmedAmountBtnClick(itemId)
                    tvConfirmedAmount.text =
                        tvConfirmedAmount.text.toString().toInt().plus(1).toString()
                }
            }
            R.id.btn_minus -> {
                if (tvConfirmedAmount.text.toString().toInt() > 0) {
                    bindingHelper.onMinusOrderItemConfirmedAmountBtnClick(itemId)
                    tvConfirmedAmount.text =
                        tvConfirmedAmount.text.toString().toInt().minus(1).toString()

                }
            }
        }
    }
}
