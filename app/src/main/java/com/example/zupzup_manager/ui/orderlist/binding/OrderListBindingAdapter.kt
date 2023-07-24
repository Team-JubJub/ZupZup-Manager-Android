package com.example.zupzup_manager.ui.orderlist.binding

import android.graphics.drawable.GradientDrawable
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.zupzup_manager.domain.models.OrderModel
import com.example.zupzup_manager.domain.models.OrderSpecificModel
import com.example.zupzup_manager.ui.common.OrderStateMapper
import com.example.zupzup_manager.ui.common.UiState
import com.example.zupzup_manager.ui.common.toSimpleDateFormat
import com.example.zupzup_manager.ui.orderlist.recyclerview.OrderListRcvAdapter

@BindingAdapter("uiState", "filter")
fun bindOrderListToRecyclerView(
    recyclerView: RecyclerView,
    uiState: UiState<List<OrderModel>>?,
    filter: String
) {
    when (uiState) {
        is UiState.Success -> {
            val orderList: List<OrderModel> = when (filter) {
                "NEW" -> {
                    uiState.data.filter { it.orderStatus == "NEW" }
                }

                "CONFIRM" -> {
                    uiState.data.filter { it.orderStatus == "CONFIRM" }
                }

                else -> {
                    uiState.data.filter { it.orderStatus != "NEW" && it.orderStatus != "CONFIRM" }
                }
            }
            (recyclerView.adapter as OrderListRcvAdapter).submitList(orderList)
        }

        is UiState.Error -> {
            Toast.makeText(recyclerView.context, uiState.errorMessage, Toast.LENGTH_SHORT).show()
        }

        else -> {
            Toast.makeText(recyclerView.context, "로딩중", Toast.LENGTH_SHORT).show()
        }
    }
}

@BindingAdapter("orderStatus")
fun bindOrderStatusToTextView(
    constraintLayout: ConstraintLayout,
    orderStatus: String
) {
    (constraintLayout.background as GradientDrawable).setColor(
        ContextCompat.getColor(
            constraintLayout.context,
            OrderStateMapper.getStateBackgroundColor(orderStatus)
        )
    )
}

@BindingAdapter("orderId")
fun bindOrderDateToTextView(
    textView: TextView,
    orderId: Long
) {
    textView.text = orderId.toSimpleDateFormat()
}