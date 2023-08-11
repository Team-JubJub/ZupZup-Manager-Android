package com.example.zupzup_manager.ui.orderdetail.binding

import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.zupzup_manager.R
import com.example.zupzup_manager.domain.models.order.OrderSpecificModel
import com.example.zupzup_manager.ui.common.OrderStateMapper
import com.example.zupzup_manager.ui.common.toDecimalFormat
import com.example.zupzup_manager.ui.orderdetail.models.OrderDetailViewType
import com.example.zupzup_manager.ui.orderdetail.recyclerview.OrderDetailRcvAdapter


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
        textView.text = "신규 예약"
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

@BindingAdapter("detailOrderTime")
fun bindOrderTimeToTextView(
    textView: TextView,
    orderTime: String
) {
    val year = orderTime.substring(0, 4)
    val month = orderTime.substring(5, 7)
    val day = orderTime.substring(8, 10)
    val time = orderTime.substring(11, 16)

    textView.text = year + "년 " + month + "월 " + day + "일 " + time + " (예약한 시간)"
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
